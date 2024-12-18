package com.example.capstone3.Api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiException extends RuntimeException {
public ApiException(String message) {
    super(message);
}
}
