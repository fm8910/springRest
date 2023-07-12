package com.ni.fmgarcia.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserResponse {

    private UUID id;
    private String name;
    private String email;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;


    public UserResponse(UUID id, String name, String email, LocalDateTime created,
                        LocalDateTime lastLogin, String token, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.created = created;
        this.lastLogin = lastLogin;
        this.token = token;
        this.isActive = isActive;
    }
}
