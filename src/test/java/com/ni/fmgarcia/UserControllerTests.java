package com.ni.fmgarcia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ni.fmgarcia.config.security.SecurityConfiguration;
import com.ni.fmgarcia.controller.UserController;
import com.ni.fmgarcia.model.dto.PhoneSignUpRequest;
import com.ni.fmgarcia.model.dto.UserResponse;
import com.ni.fmgarcia.model.dto.UserSignUpRequest;
import com.ni.fmgarcia.service.JwtService;
import com.ni.fmgarcia.service.UserDetailService;
import com.ni.fmgarcia.service.UserServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class)
@Import({SecurityConfiguration.class})
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserDetailService userDetailService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testRegisterNewUser() throws Exception {
        UserResponse user = UserResponse.builder().
                id(UUID.randomUUID()).
                name("Juan Rodriguez").
                email("juan@rodriguez.org").
                created(LocalDateTime.now()).
                lastLogin(LocalDateTime.now()).
                token("").
                isActive(true).build();
        Mockito.when(userService.saveUser(Mockito.any(UserSignUpRequest.class))).thenReturn(user);
        PhoneSignUpRequest phoneDTO = new PhoneSignUpRequest("1234567","1","57");
        UserSignUpRequest signupRequest = new UserSignUpRequest("Juan Rodriguez","juan@rodriguez.org",
                "Hunter2!",List.of(phoneDTO));

        String json = objectMapper.writeValueAsString(signupRequest);

        mockMvc.perform(post("/api/v1/users/signup")
                .header("Accept", "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.name", Matchers.is("Juan Rodriguez"))).
                andExpect(jsonPath("$.email", Matchers.is("juan@rodriguez.org")));
    }

}
