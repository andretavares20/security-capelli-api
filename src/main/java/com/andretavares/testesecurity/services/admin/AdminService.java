package com.andretavares.testesecurity.services.admin;

import java.util.List;

import com.andretavares.testesecurity.dto.SingleUserDto;
import com.andretavares.testesecurity.dto.UserDto;
import com.andretavares.testesecurity.dto.UserGoogleProviderDto;

public interface AdminService {
    
    UserDto postUser(UserDto userDto);

    UserDto postUserGoogle(UserGoogleProviderDto userDto);

    List<UserDto> getAllUsers();

    void deleteUser(Long userId);

    SingleUserDto getUserById(Long userId);

    UserDto updateUser(Long userId, UserDto userDto);

}
