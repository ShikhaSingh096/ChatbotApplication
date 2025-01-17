package ey.app.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import ey.app.chatbot.properties.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class ChatbotApplication  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ChatbotApplication.class, args);
	}

}
