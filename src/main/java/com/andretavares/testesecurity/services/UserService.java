package com.andretavares.testesecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.andretavares.testesecurity.dto.SingleUserDto;
import com.andretavares.testesecurity.dto.UserDto;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario não encontrado"));
    }

    public User create(UserDto userDto){

        if(!StringUtils.hasText(userDto.getName())){
            throw new BadRequestException("Usuario informado esta sem nome");
        }

        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new BadRequestException("Já existe um usuário com email "+userDto.getEmail()+" criado");
        }

        User user = new User(userDto.getId(),userDto.getEmail(),userDto.getPassword(),userDto.getName(),userDto.getRole(),userDto.getImg(),userDto.getEndereço(),
            userDto.getCelular(),userDto.getIsActive(),userDto.getSource());

        return userRepository.save(user);
    }

    public User edit(User user){

        if(user.getId()==null){
            throw new BadRequestException("Id do usuário não foi informado");
        }

        if(!StringUtils.hasText(user.getName())){
            throw new BadRequestException("Usuario informado esta sem nome");
        }

        return userRepository.save(user);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public SingleUserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        SingleUserDto singleUserDto = new SingleUserDto();
        optionalUser.ifPresent(user -> singleUserDto.setUserDto(user.getUserDto()));
        return singleUserDto;
    }

}
