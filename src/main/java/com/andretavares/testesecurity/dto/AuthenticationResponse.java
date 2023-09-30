package com.andretavares.testesecurity.dto;

import com.andretavares.testesecurity.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse{
    
    private String jwtToken;
    private Long id;
    private UserRole role;
    
}
