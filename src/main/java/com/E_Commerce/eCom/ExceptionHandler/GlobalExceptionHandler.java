package com.E_Commerce.eCom.ExceptionHandler;

import com.E_Commerce.eCom.Payload.APIresponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> myArgumentNotValidException(MethodArgumentNotValidException ex){

        Map<String,String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError)err).getField();
            String message = err.getDefaultMessage();
            response.put(fieldName,message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIresponse> myResourceNotFoundException( ResourceNotFoundException ex){
        String message = ex.getMessage();
        APIresponse apiResponse = new APIresponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIresponse> myAPIException(APIException ex){
        String message = ex.getMessage();
        APIresponse apiResponse = new APIresponse(message,false);
        if(Objects.equals(message, "Category name already exists.")) return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> myInvalidInputException(HttpMessageNotReadableException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", "Malformed or missing JSON input");
        response.put("details", ex.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
