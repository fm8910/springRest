package com.ni.fmgarcia.service;

import com.ni.fmgarcia.model.dto.UserResponse;
import com.ni.fmgarcia.model.dto.UserSignUpRequest;
import com.ni.fmgarcia.model.dto.UserSigninRequest;
import com.ni.fmgarcia.model.entity.User;
import com.ni.fmgarcia.repository.UserRepository;
import com.ni.fmgarcia.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Transactional
    public User saveUser(UserSignUpRequest userRequest) {
        String token = jwtService.generateTokenFromUser(userRequest.getEmail());
        var user = User.builder().id(UUID.randomUUID()).name(userRequest.getName())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .token(token).lastLogin(LocalDateTime.now()).email(userRequest.getEmail())
                .created(LocalDateTime.now()).isActive(true).build();
        return userRepository.save(user);
    }

    public String signin(UserSigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email o contrasena incorrecta."));
        String token= jwtService.generateTokenFromUser(user.getEmail());
        user.setToken(token);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        return token;
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }


    public User createOrReplaceUser(String id, UserSignUpRequest userRequest) {
        return userRepository.findById(UUID.fromString(id))
                .map(user -> {
                    user.setName(userRequest.getName());
                    user.setModified(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .orElseGet(() -> this.saveUser(userRequest));
    }

    public void deleteUserById(String id) {
        User user = returnAndValidateUserExistsById(id);
        userRepository.delete(user);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.
                stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), user.getEmail(),
                        user.getCreated(), user.getLastLogin(), user.getToken(), user.getIsActive())).
                collect(Collectors.toList());
    }

    private User returnAndValidateUserExistsById(String id) {
        return userRepository.findById(UUID.fromString(id)).
                orElseThrow(() -> new NotFoundException("El usuario no existe."));
    }


}
