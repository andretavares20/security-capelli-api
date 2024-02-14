package com.andretavares.testesecurity.controllers;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.AuthenticationRequest;
import com.andretavares.testesecurity.dto.AuthenticationResponse;
import com.andretavares.testesecurity.dto.Token;
import com.andretavares.testesecurity.dto.UserDto;
import com.andretavares.testesecurity.dto.UserGoogleProviderDto;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.repositories.UserRepository;
import com.andretavares.testesecurity.services.UserService;
import com.andretavares.testesecurity.services.auth.AuthService;
import com.andretavares.testesecurity.utils.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @Operation(summary = "Endpoint de Login", description = "Envie para esse endpoint o corpo do login, contendo o email e a senha do usuário, obtenha o token de acesso o id do usuário e a Role a qual ele pertence.")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response)
            throws IOException, JSONException {

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect Email or password");
        } catch (DisabledException disabledException) {
            throw new BadCredentialsException("User is not created");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        String role = "";
        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
            role = grantedAuthority.getAuthority().toString();
        }

        final String jwtToken = jwtUtil.generateToken(userDetails.getUsername(), role);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtToken, optionalUser.get().getId(),
                optionalUser.get().getRole());

        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Access-Control-Allow-Headers",
                "Authorization,X-Pingother,Origin,X-Requested-With,Content-Type,Accept,X-Custom-header");
        response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwtToken);

        return ResponseEntity.ok().body(authenticationResponse);

    }

    @Operation(summary = "Cria um usuário nativo do sistema", description = "Você precisa enviar as informações que deseja que o usuário tenha.")
    @PostMapping("/register")
    public ResponseEntity<User> create(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDto));
    }

    @Operation(summary = "Cria um usuário do google", description = "Envie para esse endpoint o json de resposta da api de login do Google.")
    @PostMapping("/google")
    public ResponseEntity<AuthenticationResponse> addUserGoogle(
            @RequestBody UserGoogleProviderDto userGoogleProviderDto, HttpServletResponse response) {
        UserDto createdUserDto = userService.postUserGoogle(userGoogleProviderDto);
        if (createdUserDto == null) {
            return null;
        } else {

            String fakePassword = "fakePassword";

            try {

                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(createdUserDto.getEmail(),
                                fakePassword));

            } catch (BadCredentialsException e) {
                throw new BadCredentialsException("Incorrect Email or password");
            } catch (DisabledException disabledException) {
                throw new BadCredentialsException("User is not created");
            }

            final UserDetails userDetails = userDetailsService.loadUserByUsername(createdUserDto.getEmail());

            Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
            String role = "";
            for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
                role = grantedAuthority.getAuthority().toString();
            }

            final String jwtToken = jwtUtil.generateToken(userDetails.getUsername(), role);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtToken,
                    optionalUser.get().getId(), optionalUser.get().getRole());

            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            response.setHeader("Access-Control-Allow-Headers",
                    "Authorization,X-Pingother,Origin,X-Requested-With,Content-Type,Accept,X-Custom-header");
            response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwtToken);

            return ResponseEntity.ok().body(authenticationResponse);
        }
    }

    @Operation(summary = "Cria um usuário do facebook", description = "Envie para esse endpoint o token de resposta da api de login do Facebook.")
    @PostMapping("/facebook")
    public ResponseEntity<AuthenticationResponse> loginWithFacebook(@RequestBody Token credential,HttpServletResponse response) {
        try {

            User user = userService.postUserFacebook(credential.getToken());

            String fakePassword = "fakePassword";

            try {

                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),
                                fakePassword));

            } catch (BadCredentialsException e) {
                throw new BadCredentialsException("Incorrect Email or password");
            } catch (DisabledException disabledException) {
                throw new BadCredentialsException("User is not created");
            }

            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
            String role = "";
            for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
                role = grantedAuthority.getAuthority().toString();
            }

            final String jwtToken = jwtUtil.generateToken(userDetails.getUsername(), role);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtToken,
                    optionalUser.get().getId(), optionalUser.get().getRole());

            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            response.setHeader("Access-Control-Allow-Headers",
                    "Authorization,X-Pingother,Origin,X-Requested-With,Content-Type,Accept,X-Custom-header");
            response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwtToken);

            return ResponseEntity.ok().body(authenticationResponse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
