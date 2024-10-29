package ey.app.chatbot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ey.app.chatbot.entity.UserRegistraionEntity;

@Repository
public interface UserRegistrationRepo extends JpaRepository<UserRegistraionEntity, Integer>{
	
	Optional<UserRegistraionEntity> findByEmailIdOrMobileNo(String emailId, String mobileNo);

	 Optional<UserRegistraionEntity> findByMobileNo(String username);

}
