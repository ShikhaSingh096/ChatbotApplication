package ey.app.chatbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ey.app.chatbot.entity.ChatHistoryEntity;
import ey.app.dto.conversationDto;

@Repository
public interface ChatHistoryRepo extends JpaRepository<ChatHistoryEntity, Integer> {

	List<ChatHistoryEntity> findByUserId(Integer userId);

	@Query(value="SELECT chat_id,question FROM chat_history WHERE user_id =:userId ORDER BY id ASC LIMIT 1;",nativeQuery = true)
	conversationDto findByUserIdAndId(Integer userId);

}
