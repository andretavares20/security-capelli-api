package com.andretavares.testesecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.SingleUserDto;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.repositories.UserRepository;
import com.andretavares.testesecurity.services.UserService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UserService userService;
    
    @PostMapping
    public User postUsuario(@RequestBody User User){

        return null;
    }

    @GetMapping()
    public ResponseEntity<SingleUserDto> getUsuario(@RequestParam("id") Long id){

        SingleUserDto singleUserDto = userService.getUserById(id);

        return ResponseEntity.ok().body(singleUserDto);
    }

    @PutMapping()
    public User putUsuario(@RequestParam("id") Long id){
        return null;
    }

    @DeleteMapping()
    public User deleteUsuario(@RequestParam("id") Long id){
        return null;
    }


    

}
