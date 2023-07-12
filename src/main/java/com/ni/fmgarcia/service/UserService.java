package com.ni.fmgarcia.service;

import com.ni.fmgarcia.config.security.JwtUtils;
import com.ni.fmgarcia.model.dto.UserResponse;
import com.ni.fmgarcia.model.dto.UserSignUpRequest;
import com.ni.fmgarcia.model.entity.User;
import com.ni.fmgarcia.repository.UserRepository;
import com.ni.fmgarcia.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Transactional
    public User saveUser(UserSignUpRequest userRequest){
        String token= jwtUtils.generateTokenFromUser(userRequest.getEmail());
        BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName(userRequest.getName());
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        user.setToken(token);
        user.setLastLogin(LocalDateTime.now());
        user.setEmail(userRequest.getEmail());
        user.setCreated(LocalDateTime.now());
        user.setIsActive(true);

        return userRepository.save(user);
    }

    public Optional<User> getUserById(UUID id){
        return userRepository.findById(id);
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User createOrReplaceUser(String id, UserSignUpRequest userRequest){
        return userRepository.findById(UUID.fromString(id))
                .map(user -> {
                    user.setName(userRequest.getName());
                    user.setModified(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .orElseGet(() -> this.saveUser(userRequest));
    }

    public void deleteUserById(String id){
        User user= returnAndValidateUserExistsById(id);
        userRepository.delete(user);
    }

    public List<UserResponse> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.
                stream()
                .map(user -> new UserResponse(user.getId(),user.getName(),user.getEmail(),
                        user.getCreated(),user.getLastLogin(),user.getToken(),user.getIsActive())).
                collect(Collectors.toList());
    }

    private User returnAndValidateUserExistsById(String id){
        return userRepository.findById(UUID.fromString(id)).
                orElseThrow(() -> new NotFoundException("El usuario no existe."));
    }


}
