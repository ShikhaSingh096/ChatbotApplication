package ey.app.chatbot.repository;

import ey.app.chatbot.entity.FrequentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrequentQuestionRepository extends JpaRepository<FrequentQuestion,Long> {


}
