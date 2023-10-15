package com.andretavares.testesecurity.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.services.admin.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
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
