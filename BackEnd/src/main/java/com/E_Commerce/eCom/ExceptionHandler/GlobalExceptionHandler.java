package com.E_Commerce.eCom.ExceptionHandler;

import com.E_Commerce.eCom.Payload.Responses.APIresponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


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
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> myInvalidInputException(HttpMessageNotReadableException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", "Malformed or missing JSON input");
        response.put("details", ex.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<APIresponse> myUserNotFoundException(UsernameNotFoundException ex){
        ex.printStackTrace();
        String message = "Invalid Username or Password" ;
        APIresponse apiResponse = new APIresponse(message,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> myIncorrectCredentialsEx(BadCredentialsException ex){
        ex.printStackTrace();
        APIresponse apIresponse = new APIresponse("Invalid Username or Password" ,false);
        return  new ResponseEntity<>(apIresponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        ex.printStackTrace();
        APIresponse apiResponse = new APIresponse("Something goes off ." + ex.getMessage() ,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
