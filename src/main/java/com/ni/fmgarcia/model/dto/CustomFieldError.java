package com.ni.fmgarcia.model.dto;

import lombok.Data;

@Data
public class CustomFieldError {

    private String field;

    private String message;

    public CustomFieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
