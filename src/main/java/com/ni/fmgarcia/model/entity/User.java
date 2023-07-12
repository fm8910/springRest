package com.ni.fmgarcia.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint( columnNames = "email")
        })
@Data
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    private String token;

    @NotNull
    private LocalDateTime created;

    private LocalDateTime modified;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    private Boolean isActive;

    @OneToMany(mappedBy = "user")
    private Set<Phone> phones;

}
