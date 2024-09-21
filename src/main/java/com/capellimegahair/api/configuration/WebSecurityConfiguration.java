package com.capellimegahair.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capellimegahair.api.filters.JwtRequestFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfiguration {

        private final JwtRequestFilter jwtRequestFilter;

        public WebSecurityConfiguration(JwtRequestFilter jwtRequestFilter) {
                this.jwtRequestFilter = jwtRequestFilter;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                return httpSecurity
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(requests -> requests
                                                .requestMatchers("/api/**").authenticated()
                                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                                .requestMatchers("/client/**").permitAll()
                                                .anyRequest().permitAll())
                                .sessionManagement(management -> management
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .exceptionHandling(exceptionHandling -> exceptionHandling
                                                .authenticationEntryPoint((request, response, authException) -> response
                                                                .sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                                .headers(headers -> headers
                                                .contentTypeOptions(contentTypeOptions -> contentTypeOptions.disable())
                                                .frameOptions(frameOptions -> frameOptions.deny())
                                                .httpStrictTransportSecurity(hsts -> hsts
                                                                .includeSubDomains(true)
                                                                .maxAgeInSeconds(31536000))
                                                .contentSecurityPolicy(csp -> csp
                                                                .policyDirectives(
                                                                                "default-src 'self'; script-src 'self' 'unsafe-inline' https://trustedscripts.example.com;")))
                                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                                .build();
        }

        @Bean
        @Profile("dev")
        public WebSecurityCustomizer webSecurityCustomizer() {
                return (web) -> web.ignoring()
                                .requestMatchers("/swagger-ui/**", "/v3/**", "/actuator/**"); // Ignora segurança para
                                                                                              // Swagger e Actuator em
                                                                                              // desenvolvimento
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(); // Encoder de senha BCrypt
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager(); // Gerenciador de autenticação padrão
        }

        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()
                                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication")) // Requisito
                                                                                                             // de
                                                                                                             // segurança
                                                                                                             // Bearer
                                .components(new Components().addSecuritySchemes("Bearer Authentication",
                                                createAPIKeyScheme())) // Adiciona esquema de segurança JWT
                                .info(new Info()
                                                .title("Api Capelli Megahair") // Título da API
                                                .description("Ambiente de testes das API's do Site Capelli Megahair") // Descrição
                                                                                                                      // da
                                                                                                                      // API
                                                .version("1.0") // Versão da API
                                                .contact(new Contact()
                                                                .name("André Tavares") // Contato
                                                                .email("andretavares16@gmail.com") // Email
                                                                .url("https://www.capellimegahair.com.br")) // URL do
                                                                                                            // site
                                                .license(new License()
                                                                .name("License of API") // Nome da licença
                                                                .url("API license URL"))); // URL da licença
        }

        private SecurityScheme createAPIKeyScheme() {
                return new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP) // Tipo de esquema HTTP
                                .bearerFormat("JWT") // Formato do token Bearer
                                .scheme("bearer"); // Esquema de autenticação Bearer
        }
}
