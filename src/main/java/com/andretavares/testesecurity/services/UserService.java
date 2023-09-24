package com.andretavares.testesecurity.services;

import com.andretavares.testesecurity.dto.SingleUserDto;

public interface UserService {
    
    SingleUserDto getUserById(Long userId);

}
