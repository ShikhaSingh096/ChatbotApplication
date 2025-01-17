package ey.app.chatbot.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ey.app.chatbot.entity.ChatHistoryEntity;
import ey.app.chatbot.entity.FrequentQuestion;
import ey.app.chatbot.entity.LoginEntity;
import ey.app.chatbot.entity.StateWiseImagesEntity;
import ey.app.chatbot.entity.UserRegistraionEntity;
import ey.app.dto.conversationDto;

public interface LoginService {

	LoginEntity saveLoginDetails(LoginEntity loginEntity);

	ChatHistoryEntity saveChatHistory(ChatHistoryEntity chatHistoryEntity);

	List<conversationDto> getLastQuestion(Integer userId,String chatbotId);

	List<ChatHistoryEntity> getAllChatHistory(Integer userId,String chatbotId,Integer chatId);

	String saveRegistrationDetails(UserRegistraionEntity userRegistraionEntity);

	ResponseEntity<?> uploadImages(MultipartFile file, String stateWiseImagesEntity);

	Resource loadFileAsResource(String fileName);

	List<StateWiseImagesEntity> getImageBystate(Integer stateId);
	
	Object updateFeedbackFlag(Integer chatId, Integer questionId, Integer userId,String flag);

	List<FrequentQuestion> getFAQ();

	

}
