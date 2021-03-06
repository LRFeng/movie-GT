<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh_CN" xmlns:wb="http://open.weibo.com/wb">
    <head>
        <title>movie-GT</title>
        <meta property="wb:webmaster" content="4b18a638b7db3fcc" />
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
							    <a href="user" style="text-decoration: none;color:#F8A30D;height: 60px;">${user.name}</a>
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
        
        
        <div class="content">
            <c:forEach var="movie" items="${basicInfos}">
            	<div class="content-div">
                	<div class="movie-img">
                    	<img src="${movie.path}" height="200">
                    </div>
                    <div class="movie-desc">
                        <ul>
                           <li class="movie-title">${movie.name}</li>
                           <li>上映日期: ${movie.releaseDate}</li>
                           <li>特惠价格：${movie.price}元/张</li>
                           <li>影票剩余：${movie.number}张</li>
                           <li class="movie-GT"><a href="movie?id=${movie.id}">立即抢购</a></li>
                         </ul>
                     </div>
            	</div>
            </c:forEach>   
        </div>
    
        <div class="footer">
            Copyright © 2016-12-17 中信信用卡 aring Email：aringlai@163.com
        </div>
    </body>

</html>