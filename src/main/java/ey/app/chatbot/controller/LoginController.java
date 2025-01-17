package ey.app.chatbot.controller;

import ey.app.cahtbot.dto.LoginRequest;
import ey.app.chatbot.entity.ChatHistoryEntity;
import ey.app.chatbot.entity.FrequentQuestion;
import ey.app.chatbot.entity.UserRegistraionEntity;
import ey.app.chatbot.service.LoginService;
import ey.app.chatbot.serviceImpl.AuthService;
import ey.app.dto.conversationDto;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            response.put("status", "true");
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
            response.put("status", "false");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
	
	@GetMapping("/getLastQuestion/{userId}/{chatbotId}")
	public List<conversationDto> getLastQuestion(@PathVariable Integer userId,@PathVariable String chatbotId) throws Exception {

		return loginService.getLastQuestion(userId, chatbotId);
	}
	
	@GetMapping("/getAllChatHistory/{userId}/{chatbotId}/{chatId}")
	public List<ChatHistoryEntity> getAllChatHistory(@PathVariable Integer userId,@PathVariable String chatbotId,@PathVariable Integer chatId) throws Exception {

		return loginService.getAllChatHistory(userId, chatbotId, chatId);
	}
	
	@PostMapping(value = "/uploadImages",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> uploadImages(
            @RequestParam(value = "File" , required = false) MultipartFile file,
            @RequestParam(value = "Description",required = false) String stateWiseImagesEntity
           ) { 
        try
		{
	        if (file.isEmpty()) {
	        	 Map<String, String> response = new HashMap<>();
	            return ResponseEntity.badRequest().body( response.put("message","Please upload a file"));
	        }
	       // System.out.println("Before pdf content safe");
	        // Validate PDF content
	        return new ResponseEntity<>(loginService.uploadImages(file,stateWiseImagesEntity), HttpStatus.OK);

             
        }
            
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException("Incorrect file type, Photo required");
        }

    }
 
 @GetMapping("/download/file/{fileName:.+}")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
    	Resource resource = loginService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            //LOG.info("Could not determine file type.");
        }
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
 
 
      @GetMapping("/getImageList/{stateId}")
      public ResponseEntity<?> getImageBystate(@PathVariable Integer stateId ) throws Exception {

        return new ResponseEntity<>(loginService.getImageBystate(stateId), HttpStatus.OK);
         }
	
      
      @PostMapping("/updateFeedbackFlag")
  	public ResponseEntity<?> updateFeedbackFlag(@RequestParam("chatId") Integer chatId,@RequestParam("questionId") Integer questionId,
  												@RequestParam("userId")Integer userId,@RequestParam("flag")String flag) throws Exception
  	{
  		Object chat =  loginService.updateFeedbackFlag(chatId,questionId,userId,flag);

  		return new ResponseEntity<>(chat, HttpStatus.OK);

  	}

  	@GetMapping("/getFAQ")
  	public List<FrequentQuestion> getFAQ() {

  		return loginService.getFAQ();
  	}


}
