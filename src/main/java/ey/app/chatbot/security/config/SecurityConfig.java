package ey.app.chatbot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ey.app.chatbot.security.jwt.JwtRequestFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	 
//	@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//        .csrf().disable() // Disable CSRF protection for testing (enable in production)
//        .authorizeRequests()
//            .requestMatchers("/chatbot/login").permitAll() // Allow access to the login endpoint
//            .requestMatchers("/chatbot/register").permitAll() // Allow access to the registration endpoint
//            .anyRequest().authenticated() // Require authentication for all other endpoints
//        .and()
//        .httpBasic(); // Optional: Enable basic authentication for testing
//
//    return http.build();
//    }
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf().disable() // Disable CSRF for stateless applications
	        .authorizeHttpRequests(authorize -> authorize
	        		.requestMatchers("/upfpo_preprod/chatbot/register").permitAll()
	        		.requestMatchers("/upfpo_preprod/chatbot/**").permitAll()
	        		.requestMatchers("/chatbot/**").permitAll()
	        		.requestMatchers("/chatbot/register").permitAll()
	        		.requestMatchers("/chatbot/getAllChatHistory/{userId}").permitAll()
	        		.requestMatchers("/chatbot/getLastQuestion/{userId}").permitAll()
	        		.requestMatchers("/chatbot/saveChatHistory").permitAll()
	        		.requestMatchers("/chatbot/login").permitAll() // Allow login without auth
	            .anyRequest().authenticated() // Require authentication for all other requests
	        )
	        .sessionManagement(session -> session
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless session
	        )
	        .addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class); // Add JWT filter

	    return http.build();
	}
	
	
//	@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(authorize -> authorize
//                .requestMatchers("/chatbot/login").permitAll()
//                .requestMatchers("/chatbot/register").permitAll()// Allow access to public endpoints
//                .anyRequest().authenticated()           // Any other request requires authentication
//            )
////            .formLogin(form -> form
////                .loginPage("/login")                     // Specify the custom login page
////                .permitAll()                             // Allow everyone to see the login page
////            )
////            .logout(logout -> logout
////                .permitAll()                             // Allow logout access for all
////            );
//
//        return http.build(); // Build the security filter chain
//    }

	

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoder for hashing passwords
    }

    
   
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter();
    }
}
