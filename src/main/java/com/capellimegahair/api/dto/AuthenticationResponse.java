package com.capellimegahair.api.dto;

import com.capellimegahair.api.enums.UserRole;

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
