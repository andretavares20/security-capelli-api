package com.andretavares.testesecurity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.ProdutoDto;
import com.andretavares.testesecurity.dto.SingleUserDto;
import com.andretavares.testesecurity.entities.Produto;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.services.ProdutoService;
import com.andretavares.testesecurity.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private UserService userService;


    @Operation(summary = "Cria um produto", description = "Envie para esse endpoint o JSON contendo os dados do produto e imagens")
    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody ProdutoDto produtoDto) {

        return ResponseEntity.ok().body(produtoService.create(produtoDto));

    }

    @Operation(summary = "Atualiza um produto", description = "Envie para esse endpoint o json contendo todo objeto e o id,mude apenas os dados que queira atualizar.")
    @PutMapping("/produto")
    public ResponseEntity<Produto> edit(@RequestBody Produto produto) {

        return ResponseEntity.ok().body(produtoService.edit(produto));

    }

    @Operation(summary = "Remove um produto", description = "Envie para esse endpoint o id do produto que deseja deletar")
    @DeleteMapping("/produto/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        produtoService.deleteById(id);
    }

    @Operation(summary  = "Retorna usuário pelo id.", description  = "Você precisa enviar o id do usuário.")
    @GetMapping("/{id}")
    public ResponseEntity<SingleUserDto> findById(@PathVariable("id") Long id){

        SingleUserDto singleUserDto = userService.getUserById(id);

        return ResponseEntity.ok().body(singleUserDto);
    }

    @Operation(summary  = "Retorna todos os usuários do sistema.")
    @GetMapping()
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok().body(userService.findAll());
    }

    // @PostMapping("/user")
    // public ResponseEntity<?> addUser(@RequestBody UserDto userDto){
    //     UserDto createdUserDto = adminService.postUser(userDto);
    //     if(createdUserDto == null){
    //         return new ResponseEntity<>("Something went wrong.",HttpStatus.BAD_REQUEST);
    //     }else{
    //         return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    //     }
    // } 

    // @PostMapping("/user/google")
    // public ResponseEntity<?> addUserGoogle(@RequestBody UserGoogleProviderDto userGoogleProviderDto){
    //     UserDto createdUserDto = adminService.postUserGoogle(userGoogleProviderDto);
    //     if(createdUserDto == null){
    //         return new ResponseEntity<>("Something went wrong.",HttpStatus.BAD_REQUEST);
    //     }else{
    //         return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    //     }
    // } 

    // @GetMapping("/users")
    // public ResponseEntity<List<UserDto>> getAllUsers(){
        
    //     List<UserDto> allUsers = adminService.getAllUsers();

    //     return ResponseEntity.ok(allUsers);
    // }

    // @DeleteMapping("/user/{userId}")
    // public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
    //     adminService.deleteUser(userId);
    //     return ResponseEntity.noContent().build();
    // }

    // @GetMapping("/user/{userId}")
    // public ResponseEntity<SingleUserDto> getUserById(@PathVariable Long userId){
        
    //     SingleUserDto singleUserDto = adminService.getUserById(userId);

    //     if(singleUserDto==null)
    //         return ResponseEntity.notFound().build();
    //     return ResponseEntity.ok(singleUserDto);

    // }

    // @PutMapping("/user/{userId}")
    // public ResponseEntity<?> updateUser(@PathVariable Long userId,@RequestBody UserDto userDto){
    //     UserDto createdUserDto = adminService.updateUser(userId,userDto);
    //     if(createdUserDto ==null) return new ResponseEntity<>("Something went wrong.", HttpStatus.BAD_REQUEST);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    // }

}
