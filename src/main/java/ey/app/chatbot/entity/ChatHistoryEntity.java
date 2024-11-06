package ey.app.chatbot.entity;

import java.sql.Timestamp;

import jakarta.persistence.*;
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
    
    @Column(name = "chatbot_id")
    private String chatbotId;
   
    private Timestamp create_date;
    private Timestamp update_date;


}
