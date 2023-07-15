package com.ni.fmgarcia.controller;

import com.ni.fmgarcia.model.dto.UserResponse;
import com.ni.fmgarcia.model.dto.UserSignUpRequest;
import com.ni.fmgarcia.model.entity.User;
import com.ni.fmgarcia.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserSignUpRequest userRequest) {
        User user = userService.saveUser(userRequest);
        return new ResponseEntity<>(new UserResponse(user.getId(),
                user.getName(), user.getEmail(), user.getCreated(),
                user.getLastLogin(), user.getToken(), user.getIsActive()), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return !users.isEmpty() ?
                new ResponseEntity<>(users, HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable(value = "id") String id,
                                        @Valid @RequestBody UserSignUpRequest userRequest) {
        User user = userService.createOrReplaceUser(id,userRequest);
        return  new ResponseEntity<>(new UserResponse(user.getId(),
                user.getName(),user.getEmail(),user.getCreated(),
                user.getLastLogin(),user.getToken(),user.getIsActive()),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "id") String id) {
        userService.deleteUserById(id);
    }

}
