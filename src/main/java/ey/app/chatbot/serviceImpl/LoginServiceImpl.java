package ey.app.chatbot.serviceImpl;

import ey.app.chatbot.entity.ChatHistoryEntity;
import ey.app.chatbot.entity.LoginEntity;
import ey.app.chatbot.entity.UserRegistraionEntity;
import ey.app.chatbot.repository.ChatHistoryRepo;
import ey.app.chatbot.repository.LoginRepo;
import ey.app.chatbot.repository.UserRegistrationRepo;
import ey.app.chatbot.service.LoginService;
import ey.app.dto.conversationDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;


@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	LoginRepo loginRepo;
	
	@Autowired
	ChatHistoryRepo chatHistoryRepo;
	
	
	@Autowired
	UserRegistrationRepo userRegistrationRepo;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender emailSender;
	
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
	public List<ChatHistoryEntity> getAllChatHistory(Integer userId,String chatbotId) {
		
		List<ChatHistoryEntity> chatHistorydetails = chatHistoryRepo.findByUserIdAndChatbotId(userId, chatbotId);
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
	
}
