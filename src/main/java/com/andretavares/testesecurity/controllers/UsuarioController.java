package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.andretavares.testesecurity.dto.FBUser;
import com.andretavares.testesecurity.dto.FBUserInfo;
import com.andretavares.testesecurity.dto.SingleUserDto;
import com.andretavares.testesecurity.dto.UserDto;
import com.andretavares.testesecurity.dto.UserGoogleProviderDto;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.enums.UserRole;
import com.andretavares.testesecurity.services.UserService;
import com.andretavares.testesecurity.source.RegistrationSource;

import io.swagger.models.Response;

@RestController
@RequestMapping("/api")
public class UsuarioController{

    @Autowired
    private UserService userService;
    
    @PostMapping("/user")
    public ResponseEntity<User> create(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDto));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<SingleUserDto> findById(@PathVariable("id") Long id){

        SingleUserDto singleUserDto = userService.getUserById(id);

        return ResponseEntity.ok().body(singleUserDto);
    }

    @PutMapping("/user")
    public ResponseEntity<User> edit(@RequestBody UserDto userDto){
        return ResponseEntity.ok().body(userService.edit(userDto));
    }

    @DeleteMapping("/user/{id}")
    public void deleteById(@PathVariable("id") Long id){
        userService.deleteById(id);
    }

    @PostMapping("/user/google")
    public ResponseEntity<?> addUserGoogle(@RequestBody UserGoogleProviderDto userGoogleProviderDto){
        UserDto createdUserDto = userService.postUserGoogle(userGoogleProviderDto);
        if(createdUserDto == null){
            return new ResponseEntity<>("Something went wrong.",HttpStatus.BAD_REQUEST);
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
        }
    } 

    @PostMapping("/user/facebook")
    public ResponseEntity<?> loginWithFacebook(@RequestBody String credential) {
        try {

            User user = userService.postUserFacebook(credential);

            return ResponseEntity.ok().body(user);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    

}
