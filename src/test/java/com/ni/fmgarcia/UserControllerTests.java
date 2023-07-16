package com.ni.fmgarcia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ni.fmgarcia.controller.UserController;
import com.ni.fmgarcia.model.dto.PhoneSignUpRequest;
import com.ni.fmgarcia.model.dto.UserResponse;
import com.ni.fmgarcia.model.dto.UserSignUpRequest;
import com.ni.fmgarcia.model.entity.User;
import com.ni.fmgarcia.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class UserControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetAll() throws Exception {
        UserResponse userResponse = new UserResponse(UUID.randomUUID(),"pepito perez",
                "pepito@gmail.com", LocalDateTime.now(), LocalDateTime.now(),"",true);

        List<UserResponse> userResponseList = List.of(userResponse);

        Mockito.when(userService.getAllUsers()).thenReturn(userResponseList);

        mockMvc.perform(get("/user")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", Matchers.hasSize(1))).
                andExpect(jsonPath("$[0].name", Matchers.is("pepito perez")));
    }

    @Test
    public void testRegisterNewUser() throws Exception {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Juan Rodriguez");
        user.setEmail("juan@rodriguez.org");
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setPassword("");
        user.setToken("");
        user.setIsActive(true);

        Mockito.when(userService.saveUser(Mockito.any(UserSignUpRequest.class))).thenReturn(user);
        PhoneSignUpRequest phoneDTO = new PhoneSignUpRequest("1234567","1","57");
        UserSignUpRequest signupRequest = new UserSignUpRequest("Juan Rodriguez","juan@rodriguez.org",
                "Hunter2!",List.of(phoneDTO));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(signupRequest);

        mockMvc.perform(post("/user")
                .header("Accept", "application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.name", Matchers.is("Juan Rodriguez"))).
                andExpect(jsonPath("$.email", Matchers.is("juan@rodriguez.org")));
    }

}
