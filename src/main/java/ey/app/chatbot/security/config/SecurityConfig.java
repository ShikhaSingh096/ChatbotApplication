package ey.app.chatbot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	 
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
        .csrf().disable() // Disable CSRF protection for testing (enable in production)
        .authorizeRequests()
            .requestMatchers("/chatbot/login").permitAll() // Allow access to the login endpoint
            .requestMatchers("/chatbot/register").permitAll() // Allow access to the registration endpoint
            .anyRequest().authenticated() // Require authentication for all other endpoints
        .and()
        .httpBasic(); // Optional: Enable basic authentication for testing

    return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoder for hashing passwords
    }

}
