package com.ni.fmgarcia.model.dto;

import com.ni.fmgarcia.util.beanValidation.CustomPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String email;

    @NotBlank(message = "Favor ingresar una contraseña")
    @CustomPattern( regexp = "nisum.app.regex.password",
            message = "La contraseña debe contener al menos un numero [0-9].\n" +
            "   La contraseña debe contener al menos un caracter en minusculas [a-z].\n" +
            "   La contraseña debe contener al menos un caracter en mayusculas [A-Z].\n" +
            "   La contraseña debe contener al menos un caracter especial ejemplo: ! @ # & ( ).\n" +
            "   La contraseña debe tener un tamaño de al menos 8 caracteres y un maximo de 20 caracteres.")
    private String password;

    @NotNull(message = "Favor agregar al menos un telefono")
    @Size(min = 1)
    private List<PhoneSignUpRequest> phones;


}
