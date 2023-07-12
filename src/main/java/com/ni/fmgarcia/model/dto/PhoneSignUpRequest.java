package com.ni.fmgarcia.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhoneSignUpRequest {

    private String number;

    private String cityCode;

    private String countryCode;

}
