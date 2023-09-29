package com.andretavares.testesecurity.services.auth;

import com.andretavares.testesecurity.dto.SignupRequest;
import com.andretavares.testesecurity.dto.UserDto;

public interface AuthService {
    
    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);

}
