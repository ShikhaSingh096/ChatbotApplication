package ey.app.chatbot.entity;


import javax.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name="users")
public class LoginEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;
    private String mobile_no;
    private String password;
    private String created_by;
    private String updated_by;
    private Timestamp create_date;
    private Timestamp update_date;
}
