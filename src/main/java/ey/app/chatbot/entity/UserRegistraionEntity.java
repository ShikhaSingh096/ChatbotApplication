package ey.app.chatbot.entity;

import java.time.LocalDateTime;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "user_registration")
public class UserRegistraionEntity {
	
        @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "user_id")
	    private Long userId;

	    @Column(name = "user_firstname", nullable = false, length = 100)
	    private String userFirstname;

	    @Column(name = "user_middlename", length = 100)
	    private String userMiddlename;

	    @Column(name = "user_lastname", nullable = false, length = 100)
	    private String userLastname;

	    @Column(name = "email_id", nullable = false, unique = true, length = 255)
	    private String emailId;

	    @Column(name = "mobile_no", nullable = false, unique = true, length = 15)
	    private String mobileNo;

	    @Column(name = "password", nullable = false, length = 255)
	    private String password;

	    @Column(name = "create_date", updatable = false)
	    private LocalDateTime createDate = LocalDateTime.now();

	    @Column(name = "update_date")
	    private LocalDateTime updateDate = LocalDateTime.now();

	    @Column(name = "create_by", length = 100)
	    private String createBy;

	    @Column(name = "update_by", length = 100)
	    private String updateBy;
	    
	    @Transient
	    private String token;

}
