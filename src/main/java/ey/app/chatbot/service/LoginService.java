package ey.app.chatbot.service;

import java.util.List;


import ey.app.chatbot.entity.ChatHistoryEntity;
import ey.app.chatbot.entity.LoginEntity;
import ey.app.chatbot.entity.UserRegistraionEntity;
import ey.app.dto.conversationDto;

public interface LoginService {

	LoginEntity saveLoginDetails(LoginEntity loginEntity);

	ChatHistoryEntity saveChatHistory(ChatHistoryEntity chatHistoryEntity);

	conversationDto getLastQuestion(Integer userId);

	List<ChatHistoryEntity> getAllChatHistory(Integer userId);

	String saveRegistrationDetails(UserRegistraionEntity userRegistraionEntity);

	

}
