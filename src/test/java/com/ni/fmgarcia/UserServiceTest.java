package com.ni.fmgarcia;

import com.ni.fmgarcia.config.security.JwtUtils;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveUser() {
        PhoneSignUpRequest phoneDTO = new PhoneSignUpRequest("1234567","1","57");
        UserSignUpRequest signupRequest = new UserSignUpRequest("Juan Rodriguez","juan@rodriguez.org",
                "hunter2", List.of(phoneDTO));
        userService.saveUser(signupRequest);
        // verify if the save method is called when saveUser is called too
        verify(userRepository, times(1)).save(any(User.class));
    }


}
