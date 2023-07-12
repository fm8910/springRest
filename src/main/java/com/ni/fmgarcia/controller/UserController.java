package com.ni.fmgarcia.controller;

import com.ni.fmgarcia.model.dto.UserResponse;
import com.ni.fmgarcia.model.dto.UserSignUpRequest;
import com.ni.fmgarcia.model.entity.User;
import com.ni.fmgarcia.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserSignUpRequest userRequest) {
        User user = userService.saveUser(userRequest);
        return ResponseEntity.ok(new UserResponse(user.getId(),
                user.getName(),user.getEmail(),user.getCreated(),
                user.getLastLogin(),user.getToken(),user.getIsActive()));
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return !users.isEmpty() ?
                new ResponseEntity<>(users, HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") String id,
                                        @Valid @RequestBody UserSignUpRequest userRequest) {
        User user = userService.createOrReplaceUser(id,userRequest);
        return  ResponseEntity.ok(new UserResponse(user.getId(),
                user.getName(),user.getEmail(),user.getCreated(),
                user.getLastLogin(),user.getToken(),user.getIsActive()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") String id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

}
