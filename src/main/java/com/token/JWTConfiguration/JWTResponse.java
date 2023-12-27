package com.token.JWTConfiguration;

import java.util.Date;

public class JWTResponse {

private String Token;
private Date date;
private String tracker;
public String getToken() {
	return Token;
}
public void setToken(String token) {
	Token = token;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public String getTracker() {
	return tracker;
}
public void setTracker(String tracker) {
	this.tracker = tracker;
}



}
