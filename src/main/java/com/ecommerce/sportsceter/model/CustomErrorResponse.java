package com.ecommerce.sportsceter.model;    
    
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {

    private HttpStatus status;
    private String message;
    private String error;
}
