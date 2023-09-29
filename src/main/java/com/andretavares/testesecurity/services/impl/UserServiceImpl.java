// package com.andretavares.testesecurity.services.impl;

// import java.util.Optional;

// import org.springframework.stereotype.Service;

// import com.andretavares.testesecurity.dto.SingleUserDto;
// import com.andretavares.testesecurity.entities.User;
// import com.andretavares.testesecurity.repositories.UserRepository;
// import com.andretavares.testesecurity.services.UserService;

// @Service
// public class UserServiceImpl implements UserService {

//     private final UserRepository userRepository;

//     public UserServiceImpl(UserRepository userRepository) {
//         this.userRepository = userRepository;
//     }
    
//     @Override
//     public SingleUserDto getUserById(Long userId) {
//         Optional<User> optionalUser = userRepository.findById(userId);
//         SingleUserDto singleUserDto = new SingleUserDto();
//         optionalUser.ifPresent(user -> singleUserDto.setUserDto(user.getUserDto()));
//         return singleUserDto;
//     }

// }
