/*package ey.app.chatbot.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	private String secretkey = "";

	public JWTService() {
	    try {
	        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
	        SecretKey sk = keyGen.generateKey();
	        secretkey = Base64.getUrlEncoder().encodeToString(sk.getEncoded()); // URL-safe encoding
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e);
	    }
	    }

	    public String generateToken(String mobileNo) {
	        //  return 
	          		
	         String token = 		Jwts.builder()
	                  .setSubject(String.valueOf(mobileNo))
	                  .setIssuedAt(new Date())
	                  .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
	                  .signWith(getKey()) 
	                  .compact();
	          
	          
	          System.out.println("Generated Token: " + token);
	          
	          return token;
	      }



	    private SecretKey getKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }

	    public String extractUserName(String token) {
	        // extract the username from jwt token
	        return extractClaim(token, Claims::getSubject);
	    }

	    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimResolver.apply(claims);
	    }

	    private Claims extractAllClaims(String token) {
	        return Jwts.parserBuilder()  // Use parserBuilder() instead of parser()
	                .setSigningKey(getKey())  // Use setSigningKey() to set the secret key
	                .build()
	                .parseClaimsJws(token)  // Use parseClaimsJws() to parse the JWT
	                .getBody();  // Extract claims from the parsed JWT
	    }


	    public boolean validateToken(String token, UserDetails userDetails) {
	        final String userName = extractUserName(token);
	        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }

	    private boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	    private Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }


} */
