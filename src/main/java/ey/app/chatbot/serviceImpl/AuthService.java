package ey.app.chatbot.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ey.app.chatbot.entity.UserRegistraionEntity;
import ey.app.chatbot.repository.UserRegistrationRepo;
import ey.app.chatbot.security.jwt.JwtUtils;

@Service
public class AuthService {
	
	  @Autowired
	    private UserRegistrationRepo userRegistrationRepository;

	    @Autowired
	    private JwtUtils jwtUtil;

	    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	    public UserRegistraionEntity authenticateUser(String mobileNo, String password) {
	        Optional<UserRegistraionEntity> userOptional = userRegistrationRepository.findByMobileNo(mobileNo);
	        UserRegistraionEntity user1 = null ;
             System.out.println("MB "+mobileNo +"PASS"+password);
             System.out.println("USER  "+userOptional);
            
	        if (userOptional.isPresent()) {
	        	UserRegistraionEntity user = userOptional.get();
	        	 System.out.println("USER =========>"+user);
	        	 System.out.println("USER after"+userOptional);
	        	 System.out.println("Password in JSON "+password);
	        	 System.out.println("Password in Db "+user.getPassword());
	           // if (passwordEncoder.matches(password, user.getPassword())) {
	            	if (passwordEncoder.matches(password, user.getPassword())) {
	                String token = jwtUtil.generateToken(mobileNo);
	                user.setToken(token);
	                System.out.println("token  "+token);
	                return user;
	            }
	            else return null;
	        }
	       // else user =null;
	       // System.out.println("USER  "+user);
			return user1;
	    }
	    
	}


