package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.SingleUserDto;
import com.andretavares.testesecurity.dto.UserDto;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.services.UserService;

@RestController
@RequestMapping("/api")
public class UsuarioController{

    @Autowired
    private UserService userService;
    
    @PostMapping("/user")
    public User create(@RequestBody UserDto userDto){
        return userService.create(userDto);
    }

    @GetMapping("/users")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<SingleUserDto> findById(@PathVariable("id") Long id){

        SingleUserDto singleUserDto = userService.getUserById(id);

        return ResponseEntity.ok().body(singleUserDto);
    }

    @PutMapping("/user")
    public User edit(@RequestBody UserDto userDto){
        return userService.edit(userDto);
    }

    @DeleteMapping("/user/{id}")
    public void deleteById(@PathVariable("id") Long id){
        userService.deleteById(id);
    }


    

}
