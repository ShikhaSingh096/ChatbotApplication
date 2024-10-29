package ey.app.chatbot.response;

import java.io.Serializable;

import ey.app.chatbot.entity.LoginEntity;

public class LoginResponse implements Serializable {
	
	private String token;
	private LoginEntity user;
	private String userRole;
	private Integer masterId;

	
	public LoginResponse(String token, LoginEntity user, String userRole, Integer masterId) {
		super();
		this.token = token;
		this.user = user;
		this.userRole = userRole;
		this.masterId = masterId;
	}

	public String getToken() {
		return token;
	}

	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public Integer getMasterId() {
		return masterId;
	}
	public void setMasterId(Integer masterId) {
		this.masterId = masterId;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public LoginEntity getUser() {
		return user;
	}

	public void setUser(LoginEntity user) {
		this.user = user;
	}


}

