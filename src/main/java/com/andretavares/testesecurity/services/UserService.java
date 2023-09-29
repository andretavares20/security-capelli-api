package com.andretavares.testesecurity.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.andretavares.testesecurity.dto.SingleUserDto;
import com.andretavares.testesecurity.dto.UserDto;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario com id " + id + " não encontrado"));
    }

    public User create(UserDto userDto) {

        if (!StringUtils.hasText(userDto.getEmail())) {
            throw new BadRequestException("Usuario informado esta sem email");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new BadRequestException("Já existe um usuário com email " + userDto.getEmail() + " criado");
        }

        User user = new User(userDto.getId(), userDto.getEmail(),
                new BCryptPasswordEncoder().encode(userDto.getPassword()), userDto.getName(), userDto.getRole(),
                userDto.getEndereço(),
                userDto.getCelular(), userDto.getIsActive(), userDto.getSource(), userDto.getDataNascimento(),
                userDto.getGenero());

        return userRepository.save(user);
    }

    public User edit(UserDto userDto) {

        if (userDto.getId() == null) {
            throw new BadRequestException("Id do usuário não foi informado");
        }

        User existingUser = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        BeanUtils.copyProperties(userDto, existingUser, getNullPropertyNames(userDto));

        return userRepository.save(existingUser);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public SingleUserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        SingleUserDto singleUserDto = new SingleUserDto();
        optionalUser.ifPresent(user -> singleUserDto.setUserDto(user.getUserDto()));
        return singleUserDto;
    }

}
