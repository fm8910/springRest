package com.ni.fmgarcia.service;

import com.ni.fmgarcia.model.dto.UserResponse;
import com.ni.fmgarcia.model.dto.UserSignUpRequest;
import com.ni.fmgarcia.model.dto.UserSigninRequest;
import com.ni.fmgarcia.model.entity.User;
import com.ni.fmgarcia.repository.UserRepository;
import com.ni.fmgarcia.exception.NotFoundException;
import com.ni.fmgarcia.util.mapper.UserResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserResponseMapper userResponseMapper;


    @Transactional
    public UserResponse saveUser(UserSignUpRequest userRequest) {
        String token = jwtService.generateTokenFromUser(userRequest.getEmail());
        var user = User.builder().id(UUID.randomUUID()).name(userRequest.getName())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .token(token).lastLogin(LocalDateTime.now()).email(userRequest.getEmail())
                .created(LocalDateTime.now()).isActive(true).build();
        return convert(userRepository.save(user));
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



    public UserResponse createOrReplaceUser(String id, UserSignUpRequest userRequest) {
        return userRepository.findById(UUID.fromString(id))
                .map(user -> {
                    user.setName(userRequest.getName());
                    user.setModified(LocalDateTime.now());
                    return convert(userRepository.save(user));
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
                .map(this::convert).
                collect(Collectors.toList());
    }

    private User returnAndValidateUserExistsById(String id) {
        return userRepository.findById(UUID.fromString(id)).
                orElseThrow(() -> new NotFoundException("El usuario no existe."));
    }

    private UserResponse convert(User user) {
        return userResponseMapper.userEntityToUserResponse(user);
    }


}
