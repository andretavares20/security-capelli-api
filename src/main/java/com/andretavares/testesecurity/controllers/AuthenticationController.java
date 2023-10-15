package com.andretavares.testesecurity.controllers;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.andretavares.testesecurity.dto.AuthenticationRequest;
import com.andretavares.testesecurity.dto.AuthenticationResponse;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.repositories.UserRepository;
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

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @Operation(summary  = "Endpoint de Login", description  = "Envie para esse endpoint o corpo do login, contendo o email e a senha do usuário, obtenha o token de acesso o id do usuário e a Role a qual ele pertence.")
    @PostMapping("/api/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
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

        final String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

        // if (optionalUser.isPresent()) {
        //     response.getWriter().write(new JSONObject()
        //             .put("userId", optionalUser.get().getId())
        //             .put("role", optionalUser.get().getRole())
        //             .put("token", jwtToken)
        //             .toString());
        // }

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtToken, optionalUser.get().getId(), optionalUser.get().getRole());

        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Access-Control-Allow-Headers",
                "Authorization,X-Pingother,Origin,X-Requested-With,Content-Type,Accept,X-Custom-header");
        response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwtToken);

        return ResponseEntity.ok().body(authenticationResponse);

    }

    // @PostMapping("/sign-up")
    // public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {

    //     if (authService.hasUserWithEmail(signupRequest.getEmail())) {
    //         return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
    //     }

    //     UserDto userDto = authService.createUser(signupRequest);
    //     return new ResponseEntity<>(userDto, HttpStatus.OK);

    // }

}
