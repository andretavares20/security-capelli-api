package com.capellimegahair.api.services.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.capellimegahair.api.dto.SingleUserDto;
import com.capellimegahair.api.dto.UserDto;
import com.capellimegahair.api.dto.UserGoogleProviderDto;
import com.capellimegahair.api.entities.User;
import com.capellimegahair.api.enums.UserRole;
import com.capellimegahair.api.repositories.UserRepository;
import com.capellimegahair.api.source.RegistrationSource;

import jakarta.annotation.PostConstruct;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if (adminAccount == null) {
            User admin = new User();

            admin.setEmail("admin@test.com");
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);
        }
    }

    @Override
    public UserDto postUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDto.getEmail());
        if (optionalUser.isEmpty()) {
            User user = new User();
            BeanUtils.copyProperties(userDto, user);
            user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
            user.setRole(UserRole.USER);
            User createdUser = userRepository.save(user);
            UserDto createdUserDto = new UserDto();
            createdUserDto.setId(createdUser.getId());
            createdUserDto.setEmail(createdUser.getEmail());
            return createdUserDto;
        }
        return null;
    }

    @Override
    public UserDto postUserGoogle(UserGoogleProviderDto userDto) {
        List<User> optionalUser = userRepository.findAllByEmail(userDto.getEmail());

        Optional<User> userComSourceGoogle = optionalUser.stream()
            .filter(user -> user.getSource() == RegistrationSource.GOOGLE)
            .findFirst();

        if (!userComSourceGoogle.isPresent()) {
            User user = new User();
            BeanUtils.copyProperties(userDto, user);
            user.setRole(UserRole.USER);
            user.setSource(RegistrationSource.GOOGLE);
            User createdUser = userRepository.save(user);
            UserDto createdUserDto = new UserDto();
            createdUserDto.setId(createdUser.getId());
            createdUserDto.setEmail(createdUser.getEmail());
            createdUserDto.setRole(UserRole.USER);
            return createdUserDto;
        } else {

            UserDto createdUserDto = new UserDto();
            createdUserDto.setId(userComSourceGoogle.get().getId());
            createdUserDto.setEmail(userComSourceGoogle.get().getEmail());
            createdUserDto.setRole(UserRole.USER);
            return createdUserDto;

        }

    }

    @Override
    public List<UserDto> getAllUsers() {
        // TODO Auto-generated method stub
        return userRepository.findAllByRole(UserRole.USER).stream().map(User::getUserDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        // TODO Auto-generated method stub
        userRepository.deleteById(userId);
    }

    @Override
    public SingleUserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        SingleUserDto singleUserDto = new SingleUserDto();
        optionalUser.ifPresent(user -> singleUserDto.setUserDto(user.getUserDto()));
        return singleUserDto;
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            BeanUtils.copyProperties(userDto, user, "id", "password");
            User updatedUser = userRepository.save(user);
            UserDto updatedUserDto = new UserDto();
            updatedUserDto.setId(updatedUser.getId());
            return updatedUserDto;
        }
        return null;
    }

}
