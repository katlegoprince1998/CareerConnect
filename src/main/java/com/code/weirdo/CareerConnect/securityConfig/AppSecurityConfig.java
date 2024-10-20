package com.code.weirdo.CareerConnect.securityConfig;

import com.code.weirdo.CareerConnect.securityConfig.jwt.JwtValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
public class AppSecurityConfig {
   // create a filter chain
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //let's tell the app not to manage sessions, I will manage it
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize.requestMatchers("/api/**").authenticated()
                                .anyRequest()
                                .permitAll()
                ).addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(configurationSource()))
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    private CorsConfigurationSource configurationSource() {
        return request -> {
            CorsConfiguration crc = new CorsConfiguration();
             crc.setAllowedHeaders(Collections.singletonList("*"));
             crc.setAllowedMethods(Collections.singletonList("*"));
             crc.setAllowedOrigins(Collections.singletonList("*"));
             crc.setExposedHeaders(Collections.singletonList("*"));
             crc.setMaxAge(3600L);
            return crc;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
