package com.aring.bean;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MUser {
	
	@Id
	private String id;
	
	@Basic
	private String name;
	
	@Basic
	private String profileImageUrl;
	
	@Basic
	private String email;
	
	@Basic
	private	String weiboAccessToken;
	
	@Basic
	private	String qqAccessToken;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeiboAccessToken() {
		return weiboAccessToken;
	}

	public void setWeiboAccessToken(String weiboAccessToken) {
		this.weiboAccessToken = weiboAccessToken;
	}

	public String getQqAccessToken() {
		return qqAccessToken;
	}

	public void setQqAccessToken(String qqAccessToken) {
		this.qqAccessToken = qqAccessToken;
	}
	
	
	
}
