package com.andretavares.testesecurity.services.jwt;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        Optional<User> userOptional = userRepository.findFirstByEmail(email);
        if(userOptional.isEmpty()) throw new UsernameNotFoundException("Username not found",null);

        return new org.springframework.security.core.userdetails.User(userOptional.get().getEmail(),userOptional.get().getPassword(), new ArrayList<>());

    }

    public Optional<User> findByEmail(String email){
        
        return userRepository.findByEmail(email);

    }

    public void save(User user){

        userRepository.save(user);

    }

}
