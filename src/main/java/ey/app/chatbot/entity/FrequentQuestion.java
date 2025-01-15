package ey.app.chatbot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Table(name = "frequent_questions")
@Data
public class FrequentQuestion {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;

        @Column(name = "question", nullable = false)
        private String question;

        @Column(name = "response", nullable = false)
        private String response;

        @Column(name = "create_by", length = 255)
        private String createdBy;

        @Column(name = "update_by", length = 255)
        private String updatedBy;

        @Column(name = "creation_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
        private LocalDateTime creationDate;

        @Column(name = "update_date")
        private LocalDateTime updateDate;

        @Column(name = "chatbot_id", length = 255)
        private String chatbotId;
}
