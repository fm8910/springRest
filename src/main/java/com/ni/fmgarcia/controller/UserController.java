package com.ni.fmgarcia.controller;

import com.ni.fmgarcia.model.dto.UserResponse;
import com.ni.fmgarcia.model.dto.UserSignUpRequest;
import com.ni.fmgarcia.model.dto.UserSigninRequest;
import com.ni.fmgarcia.model.entity.User;
import com.ni.fmgarcia.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServiceImpl userService;

    @Operation(summary = "Obtiene todo los usuarios.")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return !users.isEmpty() ?
                new ResponseEntity<>(users, HttpStatus.OK) : new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Crear un Usuario" ,
            description = "Permite crear un usuario nuevo el cual una vez creado retorna la informacion del usuario con su token generado")
    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserResponse registerUser(@Valid @RequestBody UserSignUpRequest userRequest) {
        return userService.saveUser(userRequest);
    }


    @Operation(summary = "Obtiene el token de sesion.",
    description = "Metodo utilizado para obtener el token de sesion basado en las credenciales de un usuario valido.")
    @PostMapping("/signin")
    @ResponseStatus(code = HttpStatus.OK)
    public String signin(@RequestBody UserSigninRequest request) {
        return userService.signin(request.getEmail(), request.getPassword());
    }

    @Operation(summary = "Actualizamos o creamos un registro basado en su ID")
    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse updateUser(@PathVariable(value = "id") String id,
                                        @Valid @RequestBody UserSignUpRequest userRequest) {
       return userService.createOrReplaceUser(id,userRequest);
    }

    @Operation(summary = "Eliminamos un registro basado en su ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "id") String id) {
        userService.deleteUserById(id);
    }

}
