package com.aring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aring.bean.MUser;
import com.aring.service.MUserService;

import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

@Controller
public class WeiboController {
	
	@Autowired
	private MUserService muserService;
	
	@RequestMapping("/weibo-auth-login")
	public String authLogin(){
		String url = null;
 		try {
			Oauth oauth = new Oauth();
			url = oauth.authorize("code", "");
		} catch (WeiboException e) {
			e.printStackTrace();
		}
 		
 		return "redirect:"+url;
		
	}
	
	
	/**
	 * 用户授权成功回调
	 * @param code
	 * @return
	 */
	@RequestMapping("/weibo-login-callback")
	public ModelAndView loginCallback(String code,HttpSession session){
		  Oauth oauth = new Oauth();
		  ModelAndView mav = new ModelAndView("redirect:/");
		  try {
			  AccessToken accessToken = oauth.getAccessTokenByCode(code);
			  String access_token = accessToken.getAccessToken();
			  String uid = accessToken.getUid();
			  Users um = new Users(access_token);
			  User user = um.showUserById(uid);
			  
			  MUser mUser = new MUser();
			  mUser.setId(user.getId());
			  mUser.setName(user.getName());
			  mUser.setProfileImageUrl(user.getProfileImageUrl());
			  mUser.setWeiboAccessToken(access_token);
			  MUser user2 = muserService.addMUser(mUser);
			  session.setAttribute("user",user2);
			  session.setAttribute("loginFlag", "weibo");
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		  return mav;
	}
	
	
	/**
	 * 用户授权失败回调
	 * @param code
	 * @return
	 */
	@RequestMapping("/login-fail-callback")
	public String loginFailCallback(){
		return "index";
	}
	
	
}
