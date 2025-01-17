package ey.app.chatbot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ey.app.chatbot.security.jwt.JwtRequestFilter;
import ey.app.chatbot.security.jwt.JwtUtils;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	 private final JwtUtils jwtUtils;
	    private final UserDetailsService userDetailsService;

	    public SecurityConfig(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
	        this.jwtUtils = jwtUtils;
	        this.userDetailsService = userDetailsService;
	    }
	    
	    @Bean
	    public JwtRequestFilter jwtRequestFilter(JwtUtils jwtUtil, UserDetailsService userDetailsService) {
	        return new JwtRequestFilter(jwtUtil, userDetailsService);
	    }

	 
/*	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
       .csrf().disable() // Disable CSRF protection for testing (enable in production)
       .authorizeRequests()
           .antMatchers("/chatbot/login").permitAll() // Allow access to the login endpoint
           .antMatchers("/chatbot/register").permitAll() // Allow access to the registration endpoint
       //    .antMatchers("/chatbot/**").permitAll() // Allow access to the registration endpoint
            
		   .anyRequest().authenticated() // Require authentication for all other endpoints
       .and()
       .httpBasic(); // Optional: Enable basic authentication for testing

   return http.build();
   } */

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
	    JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(jwtUtils, userDetailsService);

	    HttpSecurity addFilterBefore = http
	        .csrf().disable() // Disable CSRF for stateless applications
	        .authorizeHttpRequests(authorize -> authorize
	        		
	        		.antMatchers("/chatbot/*").permitAll() 
	        		.antMatchers("/chatbot/register").permitAll()
	        		.antMatchers("/chatbot/saveChatHistory").permitAll()
	        		.antMatchers("/chatbot/getLastQuestion/{userId}/{chatbotId").permitAll()
	        		.antMatchers("/chatbot/uploadImages").permitAll()
	        		.antMatchers("/chatbot/download/file/{fileName:.+}").permitAll()
	        		.antMatchers("/chatbot/getImageList/{stateId}").permitAll()
	        		.antMatchers("/chatbot/updateFeedbackFlag").permitAll()
	        		.antMatchers("/chatbot/getFAQ").permitAll()
	        		.antMatchers("/chatbot/getAllChatHistory/{userId}/{chatbotId}/{chatId}").permitAll()
	        		.antMatchers("/chatbot/login").permitAll() // Allow login without auth
	            .anyRequest().authenticated() // Require authentication for all other requests
	        )
	        .sessionManagement(session -> session
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless session
	        )
	        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

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
//    } */

	

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoder for hashing passwords
    }


    
   
   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    } 
    
  
}

