package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.andretavares.testesecurity.dto.SingleUserDto;
import com.andretavares.testesecurity.dto.Token;
import com.andretavares.testesecurity.dto.UserDto;
import com.andretavares.testesecurity.dto.UserGoogleProviderDto;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.services.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/user")
public class UsuarioController{

    @Autowired
    private UserService userService;

    @Operation(summary  = "Retorna todos os usuários do sistema.")
    @GetMapping()
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok().body(userService.findAll());
    }

    @Operation(summary  = "Retorna usuário pelo id.", description  = "Você precisa enviar o id do usuário.")
    @GetMapping("/{id}")
    public ResponseEntity<SingleUserDto> findById(@PathVariable("id") Long id){

        SingleUserDto singleUserDto = userService.getUserById(id);

        return ResponseEntity.ok().body(singleUserDto);
    }

    @Operation(summary  = "Atualiza usuário", description  = "Você precisa enviar o id do usuário e tambem o que deseja atualizar do mesmo.")
    @PutMapping()
    public ResponseEntity<User> edit(@RequestBody UserDto userDto){
        return ResponseEntity.ok().body(userService.edit(userDto));
    }

    @Operation(summary  = "Deleta usuário pelo id.", description  = "Você precisa enviar o id do usuário.")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id){
        userService.deleteById(id);
    }

    @Operation(summary  = "Cria um usuário do google", description  = "Envie para esse endpoint o json de resposta da api de login do Google.")
    @PostMapping("/google")
    public ResponseEntity<?> addUserGoogle(@RequestBody UserGoogleProviderDto userGoogleProviderDto){
        UserDto createdUserDto = userService.postUserGoogle(userGoogleProviderDto);
        if(createdUserDto == null){
            return new ResponseEntity<>("Something went wrong.",HttpStatus.BAD_REQUEST);
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
        }
    } 

    @Operation(summary  = "Cria um usuário do facebook", description  = "Envie para esse endpoint o token de resposta da api de login do Facebook.")
    @PostMapping("/facebook")
    public ResponseEntity<?> loginWithFacebook(@RequestBody Token credential) {
        try {

            User user = userService.postUserFacebook(credential.getToken());

            return ResponseEntity.ok().body(user);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    

}
