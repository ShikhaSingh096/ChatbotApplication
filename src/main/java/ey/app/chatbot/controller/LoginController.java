package ey.app.chatbot.controller;

import ey.app.cahtbot.dto.LoginRequest;
import ey.app.chatbot.entity.ChatHistoryEntity;
import ey.app.chatbot.entity.UserRegistraionEntity;
import ey.app.chatbot.service.LoginService;
import ey.app.chatbot.serviceImpl.AuthService;
import ey.app.dto.conversationDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/chatbot")
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	@Autowired
    private AuthService authService;
	
	

	@PostMapping(value = "/login")
	public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest) throws Exception
	{
		
		System.out.println("HIIII---------->");
		
		String mobileNo = loginRequest.getMobileNo();
	    String password = loginRequest.getPassword();
		UserRegistraionEntity jwtToken = authService.authenticateUser(mobileNo, password);
		
		System.out.println("welcome in registration controller");

		System.out.println("jwtToken" + jwtToken);

        if (jwtToken != null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", jwtToken.getToken());
            response.put("mobileNo", jwtToken.getMobileNo());
            response.put("email", jwtToken.getEmailId());
            response.put("firstName", jwtToken.getUserFirstname());
            response.put("middleName", jwtToken.getUserMiddlename());
            response.put("lastName", jwtToken.getUserLastname());
            response.put("userId", String.valueOf(jwtToken.getUserId()));
            
           // response.put("token", jwtToken.get());
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid phone number or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
		

		
    
	

	@PostMapping(value = "/register")
	public ResponseEntity<?> register(@RequestBody UserRegistraionEntity userRegistraionEntity) throws Exception
	{
		 String responseMessage =  loginService.saveRegistrationDetails(userRegistraionEntity);

		 if ("Registered successfully".equals(responseMessage)) {
	            return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"" + responseMessage + "\"}");
	        } else {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"" + responseMessage + "\"}");
	        }

		
    }
	
	@PostMapping(value = "/saveChatHistory")
	public ResponseEntity<?> saveChatHistory(@RequestBody ChatHistoryEntity chatHistoryEntity) throws Exception
	{
		ChatHistoryEntity chat =  loginService.saveChatHistory(chatHistoryEntity);

		return new ResponseEntity<>(chat, HttpStatus.CREATED);

    }
	
	@GetMapping("/getLastQuestion/{userId}")
	public conversationDto getLastQuestion(@PathVariable Integer userId) throws Exception {

		return loginService.getLastQuestion(userId);
	}
	
	@GetMapping("/getAllChatHistory/{userId}")
	public List<ChatHistoryEntity> getAllChatHistory(@PathVariable Integer userId) throws Exception {

		return loginService.getAllChatHistory(userId);
	}
}
