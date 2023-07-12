package com.ni.fmgarcia.model.dto;

import com.ni.fmgarcia.util.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserSignUpRequest {

    @NotBlank(message = "Favor ingresar el nombre del usuario ")
    private String name;

    @NotBlank(message = "Favor ingresar el correo")
    @Size(max = 50)
    @Email(message = "Favor ingresar un correo valido")
    @UniqueEmail
    private String email;

    @NotBlank(message = "Favor ingresar una contraseña")
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "    Password must contain at least one digit [0-9].\n" +
            "    Password must contain at least one lowercase Latin character [a-z].\n" +
            "    Password must contain at least one uppercase Latin character [A-Z].\n" +
            "    Password must contain at least one special character like ! @ # & ( ).\n" +
            "    Password must contain a length of at least 8 characters and a maximum of 20 characters.")
    private String password;

    @NotNull(message = "Favor agregar al menos un telefono")
    @Size(min = 1)
    private List<PhoneSignUpRequest> phones;


}
