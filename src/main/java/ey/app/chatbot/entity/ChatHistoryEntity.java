package ey.app.chatbot.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="chat_history")
public class ChatHistoryEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Integer chat_id;
	
	@Column(name = "user_id")
	private Integer userId;
	
    private String question;
    private String answer;
    
    private String chatbot_id;
   
    private Timestamp create_date;
    private Timestamp update_date;


}
