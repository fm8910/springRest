package com.ni.fmgarcia.exception;

import com.ni.fmgarcia.model.dto.HandleMessageResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HandleMessageResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }


    private HandleMessageResponse processFieldErrors(List<FieldError> fieldErrors) {
        HandleMessageResponse handleMessageResponse = new HandleMessageResponse( "Error de validaciones");
        for (FieldError fieldError: fieldErrors) {
            handleMessageResponse.addFieldError(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return handleMessageResponse;
    }


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        HandleMessageResponse apiError = new HandleMessageResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
