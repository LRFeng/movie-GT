<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh_CN" xmlns:wb="http://open.weibo.com/wb">
    <head>
        <title>movie-GT</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link rel="stylesheet" href="./static/css/main.css" type="text/css">
        <script src="./static/js/jquery.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="./static/js/main.js"></script>
        
        <script type="text/javascript">
           $(window).load(function(){
               var winPos = 1;
               var bgVal='';
               $(window).scroll(function(event){
                    winPos = $(window).scrollTop();
                    bgVal = 'rgba(0,0,0,+'+winPos/100+')';
                    $(".nav").css("background-color",bgVal);
                    if(winPos<=110){
                        $(".line").css("width",winPos+"%");
                    }
                }); 
           });
        </script>
        
        
    </head>
    
    <body>
        <div class="nav">
            <ul>
                <li><img src="./static/img/icon.png"></li>
                <li>电影票抢购</li>
            </ul>
            <div class="nav-right">
                <ul>
                    <c:choose>
                    	<c:when test="${not empty user}">
							<li>
							    <a href="#" style="text-decoration: none;color:#F8A30D;height: 60px;">${user.name}</a>
							    <img alt="" src="${user.profileImageUrl}">
								<a href="login-out" style="text-decoration: none;color:#FFFFFF; font-size: 14px;">退出登陆</a>
							</li>
                    	</c:when>
                    	<c:otherwise>
                    		<li><a href="#" title="QQ登陆"><img src="./static/img/qq_icon.png"></a></li>
                    		<li><a href="weibo-auth-login" title="新浪微博登陆"><img src="./static/img/weibo_icon.png"></a></li>
                    	</c:otherwise>
                    </c:choose>
                </ul>
            </div>
            <div class="line"></div>
        </div>
        
        <div class="user-content">
        	 <form  action="save-user" method="post" onsubmit="return check()">
        	 	<div>完善个人信息</div>
        	 	<div><span>昵称：</span><span>${user.name}</span></div>
        	 	<div><span>邮箱：</span><input id="email" name="email" value="${user.email}" type="text" style="width: 300px; height: 25px; font-size: 1em;"><span>*</span></div>
        	 	<div><button type="submit" style="margin: 0px 100px; display: block; background-color: #F8A30D;padding: 15px 30px; border: none; font-size: 18px;color: white;">提交</button></div>
        	 </form>
        </div>
    
        <div class="footer">
            Copyright © 2016-12-17 中信信用卡 aring Email：aringlai@163.com
        </div>
        
        
        <script type="text/javascript">
        	function check() {
        		var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        		var email = $("#email").val();
				if(email == null || email==''){
        			alert("邮箱不能为空");
        			return false;
        		}else if(!filter.test(email)){
        			alert("邮箱无效");
        			return false;
        		}else{
        			return true;
        		}
        	}
        	
        </script>
        
        
        
    </body>

</html>