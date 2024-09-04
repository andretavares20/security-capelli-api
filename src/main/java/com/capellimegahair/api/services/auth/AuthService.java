package com.capellimegahair.api.services.auth;

import com.capellimegahair.api.dto.SignupRequest;
import com.capellimegahair.api.dto.UserDto;

public interface AuthService {
    
    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);

}
