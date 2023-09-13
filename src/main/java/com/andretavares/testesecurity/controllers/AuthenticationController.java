package com.andretavares.testesecurity.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.client.RestTemplate;

import com.andretavares.testesecurity.dto.AuthenticationRequest;
import com.andretavares.testesecurity.dto.AuthenticationResponse;
import com.andretavares.testesecurity.dto.FBUser;
import com.andretavares.testesecurity.dto.FBUserInfo;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.enums.UserRole;
import com.andretavares.testesecurity.repositories.UserRepository;
import com.andretavares.testesecurity.source.RegistrationSource;
import com.andretavares.testesecurity.utils.JwtUtil;

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

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization ";
    private static List<User> userList = new ArrayList<>();

    @Value("${facebook.client-id}")
    private String FACEBOOK_CLIENT_ID;

    @Value("${facebook.secret-key}")
    private String FACEBOOK_SECRET_KEY;

    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response)
            throws IOException, JSONException {

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect Email or password");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not created");
            return;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());

        final String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(jwtToken);

        if (optionalUser.isPresent()) {
            response.getWriter().write(new JSONObject()
                    .put("userId", optionalUser.get().getId())
                    .put("role", optionalUser.get().getRole())
                    .toString());
        }

        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Access-Control-Allow-Headers",
                "Authorization,X-Pingother,Origin,X-Requested-With,Content-Type,Accept,X-Custom-header");
        response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwtToken);
    }

    @PostMapping("/LoginWithFacebook")
    public ResponseEntity<?> loginWithFacebook(@RequestBody String credential) {
        try {

            credential = credential.replaceAll("\"", "");
            RestTemplate restTemplate = new RestTemplate();

            // Fazer a chamada para verificar o token do Facebook
            String debugTokenUrl = "https://graph.facebook.com/debug_token?input_token=" + credential +
                    "&access_token=" + FACEBOOK_CLIENT_ID + "|" + FACEBOOK_SECRET_KEY;

            FBUser userOBJK = restTemplate.getForObject(debugTokenUrl, FBUser.class);

            if (userOBJK != null && !userOBJK.getData().isValid()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Fazer a chamada para obter informações do usuário do Facebook
            String meUrl = "https://graph.facebook.com/me?fields=first_name,last_name,email,id&access_token="
                    + credential;
            FBUserInfo userContentObj = restTemplate.getForObject(meUrl, FBUserInfo.class);

            Optional<User> optionalUser = userRepository.findFirstByEmail(userContentObj.getEmail());

            if (optionalUser.isPresent()) {
                if (optionalUser.get().getSource() != RegistrationSource.FACEBOOK) {
                    User user = facebookUserInfoToUser(userContentObj);
                    userRepository.save(user);
                }
            } else {
                User user = facebookUserInfoToUser(userContentObj);
                userRepository.save(user);
            }

            if (userContentObj != null) {

                return ResponseEntity.ok(userContentObj);

            } else {

                return ResponseEntity.badRequest().build();

            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private User facebookUserInfoToUser(FBUserInfo fbUserInfo) {
        User user = new User();
        user.setEmail(fbUserInfo.getEmail());
        user.setName(fbUserInfo.getFirstName() + ' ' + fbUserInfo.getLastName());
        user.setRole(UserRole.USER);
        user.setSource(RegistrationSource.FACEBOOK);
        return user;
    }

}
