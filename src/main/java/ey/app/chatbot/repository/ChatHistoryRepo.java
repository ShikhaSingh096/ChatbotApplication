package ey.app.chatbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ey.app.chatbot.entity.ChatHistoryEntity;
import ey.app.dto.conversationDto;

@Repository
public interface ChatHistoryRepo extends JpaRepository<ChatHistoryEntity, Integer> {

	 ChatHistoryEntity findByChatIdAndIdAndUserId(Integer chatId, Integer questionId, Integer userId) ;

	List<ChatHistoryEntity> findByUserIdAndChatbotId(Integer userId, String chatbotId);

	@Query(value="SELECT chat_id, question, answer FROM chat_history AS ch "
			+ "WHERE user_id =:userId AND chatbot_id =:chatbotId "
			+ " AND id = ( SELECT MAX(id) FROM chat_history "
			+ " WHERE chat_id = ch.chat_id AND user_id =:userId AND chatbot_id =:chatbotId )"
			+ " ORDER BY chat_id;",nativeQuery = true)
	List<conversationDto> findByUserIdAndId(Integer userId,String chatbotId);

}
