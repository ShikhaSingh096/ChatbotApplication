package ey.app.chatbot.controller;

import ey.app.chatbot.entity.ChatHistoryEntity;
import ey.app.chatbot.entity.LoginEntity;
import ey.app.chatbot.service.LoginService;
import ey.app.dto.conversationDto;

import java.util.List;

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

	@PostMapping(value = "/login")
	public ResponseEntity<?> signin(@RequestBody LoginEntity loginEntity) throws Exception
	{
		LoginEntity user =  loginService.saveLoginDetails(loginEntity);

		return new ResponseEntity<>(user, HttpStatus.CREATED);

		
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
