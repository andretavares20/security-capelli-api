package com.andretavares.testesecurity.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.andretavares.testesecurity.filters.JwtRequestFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfiguration {

    private final JwtRequestFilter jwtRequestFilter;

    @Value("${frontend.url}")
    private String frontendUrl;

    RequestCache nullRequestCache = new NullRequestCache();

    public WebSecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // return httpSecurity.csrf().disable()
        //         .authorizeHttpRequests().requestMatchers("/authenticate","/api/**","/LoginWithFacebook","/pagamento/**").permitAll()
        //         .and()
        //         .authorizeHttpRequests().requestMatchers("/api/**").authenticated()
        //         .and()
        //         .sessionManagement()
        //         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //         .and()
        //         .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        //         .build();

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/authenticate","/api/**","/LoginWithFacebook","/pagamento/**","/swagger-ui/**","/v3/api-docs/**","/usuario/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/car").authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .requestCache((cache) -> cache
                        .requestCache(nullRequestCache))
                .build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();

    }

}
