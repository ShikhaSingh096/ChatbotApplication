package ey.app.chatbot.security.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

@Component
public class JwtUtils {
	
	
	private final String SECRET_KEY = "your_secret_key";

    public String generateToken(String mobileNo) {
      //  return 
        		
       String token = 		Jwts.builder()
                .setSubject(String.valueOf(mobileNo))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        
        
        System.out.println("Generated Token: " + token);
        
        return token;
    }

    public Boolean validateToken(String token, String phoneNumber) {
        String tokenPhoneNumber = extractPhoneNumber(token);
        return (tokenPhoneNumber.equals(phoneNumber) && !isTokenExpired(token));
    }

    public String extractPhoneNumber(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}


