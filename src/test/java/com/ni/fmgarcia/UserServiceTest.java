package com.ni.fmgarcia;

import com.ni.fmgarcia.service.JwtService;
import com.ni.fmgarcia.exception.NotFoundException;
import com.ni.fmgarcia.model.dto.PhoneSignUpRequest;
import com.ni.fmgarcia.model.dto.UserSignUpRequest;
import com.ni.fmgarcia.model.entity.User;
import com.ni.fmgarcia.repository.UserRepository;
import com.ni.fmgarcia.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    JwtService jwtService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void deleteUserById_throwsExceptionIfIDNotFound() {
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> userService.deleteUserById(UUID.randomUUID().toString()))
                .withMessage("El usuario no existe.");
    }

}
