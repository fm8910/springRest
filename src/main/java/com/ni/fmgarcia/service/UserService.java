package com.ni.fmgarcia.service;

import com.ni.fmgarcia.model.dto.UserResponse;
import com.ni.fmgarcia.model.dto.UserSignUpRequest;
import com.ni.fmgarcia.model.dto.UserSigninRequest;
import com.ni.fmgarcia.model.entity.User;

import java.util.List;

public interface UserService {

    UserResponse saveUser(UserSignUpRequest userRequest);

    String signin(UserSigninRequest request);

    UserResponse createOrReplaceUser(String id, UserSignUpRequest userRequest);

    void deleteUserById(String id);

    List<UserResponse> getAllUsers();
}
