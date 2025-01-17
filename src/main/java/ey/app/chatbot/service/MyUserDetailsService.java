package ey.app.chatbot.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ey.app.chatbot.repository.UserRegistrationRepo;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRegistrationRepo userRepository;

    public MyUserDetailsService(UserRegistrationRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByMobileNo(username)
            .map(user -> User.builder()
                .username(user.getMobileNo())
                .password(user.getPassword())
             // Assign an empty authority or no authority at all
                .authorities(Collections.emptyList()) // No authorities
             //   .roles(user.getRole()) // Adjust roles as necessary
                .build())
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
    
} 


