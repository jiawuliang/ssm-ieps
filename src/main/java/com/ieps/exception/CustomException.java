package com.ieps.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ljw
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends Exception {
    
    private String message;
    
}
