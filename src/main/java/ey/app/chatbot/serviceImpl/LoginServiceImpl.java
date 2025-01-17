package ey.app.chatbot.serviceImpl;

import ey.app.chatbot.entity.ChatHistoryEntity;
import ey.app.chatbot.entity.FrequentQuestion;
import ey.app.chatbot.entity.LoginEntity;
import ey.app.chatbot.entity.StateWiseImagesEntity;
import ey.app.chatbot.entity.UserRegistraionEntity;
import ey.app.chatbot.exception.FileStorageException;
import ey.app.chatbot.exception.ResourceNotFoundException;
import ey.app.chatbot.properties.FileStorageProperties;
import ey.app.chatbot.repository.ChatHistoryRepo;
import ey.app.chatbot.repository.FrequentQuestionRepository;
import ey.app.chatbot.repository.LoginRepo;
import ey.app.chatbot.repository.StateWiseImagesRepo;
import ey.app.chatbot.repository.UserRegistrationRepo;
import ey.app.chatbot.service.FileStorageService;
import ey.app.chatbot.service.LoginService;
import ey.app.dto.conversationDto;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	LoginRepo loginRepo;
	
	@Autowired
	ChatHistoryRepo chatHistoryRepo;
	
	@Autowired
	StateWiseImagesRepo stateWiseImagesRepo;
	
	@Autowired
	UserRegistrationRepo userRegistrationRepo;
	
	@Autowired
    FileStorageService fileStorageService;
	
	private final Path fileStorageLocation;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	FrequentQuestionRepository frequentQuestionRepository;
	
	@Value("${spring.mail.username}")
	private String FROM_EMAIL;

	

	@Override
	public LoginEntity saveLoginDetails(LoginEntity loginEntity) {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		loginEntity.setCreate_date(timestamp);
		loginEntity.setUpdate_date(timestamp);
		
		String otp = generateOTP();
		
		loginEntity.setPassword(otp);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(FROM_EMAIL);
		 
		message.setTo("aryan1999sharma1993@gmail.com");
		//message.setTo("sinshikha2096@gmail.com");
		

		message.setSubject("OTP to login");
		message.setText("Dear User" + "," + "\n\n"
				+ "You OTP to login in chatbot is - " + otp +"\n\n"+"Thank you."+ "\n\n"+"Best Regards, " + "\n" + "Team Chatbot");
		System.out.println(message.getText());
		emailSender.send(message);
		
		loginRepo.save(loginEntity);
 	   
		return loginEntity ;
		
	}
	
	 public static String generateOTP() {
	        // Generate a random number between 100000 and 999999
	        Random random = new Random();
	        int otpNumber = 100000 + random.nextInt(900000);
	        
	        // Convert the number to a string and ensure it has exactly 6 digits
	        String otp = Integer.toString(otpNumber);
	        
	        return otp;
	    }

	@Override
	public ChatHistoryEntity saveChatHistory(ChatHistoryEntity chatHistoryEntity) {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		chatHistoryEntity.setCreate_date(timestamp);
		chatHistoryEntity.setUpdate_date(timestamp);
		
		
		chatHistoryRepo.save(chatHistoryEntity);
	 	   
		return chatHistoryEntity ;
		
	}

	@Override
	public List<conversationDto> getLastQuestion(Integer userId,String chatbotId) {
		
		List<conversationDto> ConversationDto = chatHistoryRepo.findByUserIdAndId(userId, chatbotId);
		return ConversationDto;
}

	@Override
	public List<ChatHistoryEntity> getAllChatHistory(Integer userId,String chatbotId,Integer chatId) {
		
		List<ChatHistoryEntity> chatHistorydetails = chatHistoryRepo.findByUserIdAndChatbotIdAndChatId(userId, chatbotId, chatId);
		return chatHistorydetails;
		
	}

	@Override
	public String saveRegistrationDetails(UserRegistraionEntity userRegistraionEntity) {
		
		if (userRegistrationRepo.findByEmailIdOrMobileNo(userRegistraionEntity.getEmailId(), userRegistraionEntity.getMobileNo()).isPresent()) {
            return "User already exists";
        }

        // Save the new user
		
		String encodedPassword = passwordEncoder.encode(userRegistraionEntity.getPassword());
		userRegistraionEntity.setPassword(encodedPassword);
        
		userRegistrationRepo.save(userRegistraionEntity);
        return "Registered successfully";
	}



	@Override
	public ResponseEntity<?> uploadImages(MultipartFile file, String stateWiseImagesEntity4) {
		
		 ObjectMapper objectMapper = new ObjectMapper();
		 StateWiseImagesEntity stateWiseImagesEntity=new StateWiseImagesEntity();
	        JSONObject jsonObject=new JSONObject();
	        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	        try {
	            // Parse JSON string into Java object
	        	stateWiseImagesEntity =  objectMapper.readValue(stateWiseImagesEntity4, StateWiseImagesEntity.class);

		if(file != null && !file.isEmpty() && stateWiseImagesEntity != null)
		{
		StateWiseImagesEntity stateWiseImagesEntity1 = new StateWiseImagesEntity();
		//resource1.setThumbnail(filePathUtility.getFilePath(thumbnail, "Resource"));
			String fileName1 = fileStorageService.storeFile(file);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			
			if (fileName1 != null) {
	            fileName1 = fileName1.trim();
	            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                    .path("/chatbot/download/file/")
	                    .path(fileName1)
	                    .toUriString();
	            stateWiseImagesEntity1.setImage(fileDownloadUri);
	            stateWiseImagesEntity1.setDescription(stateWiseImagesEntity.getDescription());
	            stateWiseImagesEntity1.setStateId(stateWiseImagesEntity.getStateId());
	            stateWiseImagesEntity1.setCreate_date(timestamp);
	            stateWiseImagesEntity1.setCreated_by(currentPrincipalName);
	            jsonObject.put("message","Image Updated!");
	           
	        }
			
			stateWiseImagesRepo.save(stateWiseImagesEntity1);
			 return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
return null;
}

	@Override
	public Resource loadFileAsResource(String fileName) {
		try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("File not found " + fileName, ex);
        }
	}
	
	@Autowired
    public LoginServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

	@Override
	public List<StateWiseImagesEntity> getImageBystate(Integer stateId) {
        
		List<StateWiseImagesEntity> StateWiseImagesList = new ArrayList<>(); 
		
		StateWiseImagesList = stateWiseImagesRepo.findByStateId(stateId);
		
		return StateWiseImagesList;
		
		
	}
	
	@Override
	public Object updateFeedbackFlag(Integer chatId, Integer questionId, Integer userId,String flag) {
	ChatHistoryEntity chatHistoryEntity=	chatHistoryRepo.findByChatIdAndIdAndUserId(chatId,questionId,userId);
		chatHistoryEntity.setFeedbackFlag(flag);
	    chatHistoryRepo.save(chatHistoryEntity);
		return "Flag updated successfully";
	}

	@Override
	public List<FrequentQuestion> getFAQ() {
		List<FrequentQuestion> list=	frequentQuestionRepository.findAll();
		return list;
	}
	
	
}
