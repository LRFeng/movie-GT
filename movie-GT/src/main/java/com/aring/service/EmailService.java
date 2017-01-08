package com.aring.service;

public interface EmailService {

	final public static String EMAIL_TITLE = "抢票成功";
	   
	final public static String EMAIL_CONTEXT1 = "亲爱的用户：<br/> 感谢你参与本次电影票抢购活动，您已经成功抢到本场电影影票：";	

	final public static String EMAIL_CONTEXT2 = "<br/>如有问题，请你联系我们<br/><br/>----电影票抢购";	
	   
	final public static String SENDER_ADDRESS ="aring@2980.com";
	   
	final public static String SENDER_PASS ="931003";
	
	public void send(String receEmail,String title,String context) throws Exception;
	
}
