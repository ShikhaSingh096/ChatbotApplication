package ey.app.chatbot.entity;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="state_wise_images")
public class StateWiseImagesEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "state_id")
	private Integer stateId;
	
    private String description;
    private String image;
   
    private String created_by;
    private String updated_by;
   
    private Timestamp create_date;
    private Timestamp update_date;



}
