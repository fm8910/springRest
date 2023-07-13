package com.ni.fmgarcia.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HandleMessageResponse {

    private List<CustomFieldError> campos = new ArrayList<>();

    private String message;

    public HandleMessageResponse(String message) {
        this.message = message;
    }

    public void addFieldError(String field, String message) {
        CustomFieldError error = new CustomFieldError(field, message);
        campos.add(error);
    }




}
