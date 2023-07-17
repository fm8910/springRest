package com.ni.fmgarcia.controller;

import com.ni.fmgarcia.model.dto.UserResponse;
import com.ni.fmgarcia.model.dto.UserSignUpRequest;
import com.ni.fmgarcia.model.dto.UserSigninRequest;
import com.ni.fmgarcia.model.entity.User;
import com.ni.fmgarcia.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserSignUpRequest userRequest) {
        User user = userService.saveUser(userRequest);
        return new ResponseEntity<>(new UserResponse(user.getId(),
                user.getName(), user.getEmail(), user.getCreated(),
                user.getLastLogin(), user.getToken(), user.getIsActive()), HttpStatus.CREATED);
    }


    @PostMapping("/signin")
    @ResponseStatus(code = HttpStatus.OK)
    public String signin(@RequestBody UserSigninRequest request) {
        return userService.signin(request);
    }
}
