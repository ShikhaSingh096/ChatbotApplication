package ey.app.chatbot.exception;

import java.net.MalformedURLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{


    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg) {

        super(msg);
    }

public ResourceNotFoundException(String s, MalformedURLException ex) {

}
}
