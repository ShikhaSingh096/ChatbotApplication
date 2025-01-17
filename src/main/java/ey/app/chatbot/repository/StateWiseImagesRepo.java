package ey.app.chatbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ey.app.chatbot.entity.StateWiseImagesEntity;


public interface StateWiseImagesRepo extends JpaRepository<StateWiseImagesEntity, Integer>{

	

	List<StateWiseImagesEntity> findByStateId(Integer stateId);

}