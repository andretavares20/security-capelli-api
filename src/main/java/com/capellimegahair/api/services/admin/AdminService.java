package com.capellimegahair.api.services.admin;

import java.util.List;

import com.capellimegahair.api.dto.SingleUserDto;
import com.capellimegahair.api.dto.UserDto;
import com.capellimegahair.api.dto.UserGoogleProviderDto;

public interface AdminService {
    
    UserDto postUser(UserDto userDto);

    UserDto postUserGoogle(UserGoogleProviderDto userDto);

    List<UserDto> getAllUsers();

    void deleteUser(Long userId);

    SingleUserDto getUserById(Long userId);

    UserDto updateUser(Long userId, UserDto userDto);

}
