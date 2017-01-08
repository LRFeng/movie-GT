package com.aring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

@Controller
public class QQController {

	@RequestMapping("/qq-auth-login")
	public String authLogin(HttpServletRequest request){
		String url = null;
		try {
			Oauth oauth = new Oauth();
			url	= oauth.getAuthorizeURL(request);
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
		return "redirect:"+url;
	}
	
	@RequestMapping("/qq-login-callback")
	public String loginCallback(HttpServletRequest request){
		 UserInfoBean userInfoBean = null;
		 try {
			AccessToken accessToken = (new Oauth()).getAccessTokenByRequest(request);
			String accessTokenStr = accessToken.getAccessToken();
			OpenID openIDObj =  new OpenID(accessTokenStr);
			UserInfo qzoneUserInfo = new UserInfo(accessTokenStr, openIDObj.getUserOpenID());
            userInfoBean = qzoneUserInfo.getUserInfo();
			System.out.println(userInfoBean.toString());
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
		
		return userInfoBean.toString();
	}
	
	
	
	
	
	
}
