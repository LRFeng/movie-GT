<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh_CN" xmlns:wb="http://open.weibo.com/wb">
    <head>
        <title>${movie.name}</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link rel="stylesheet" href="./static/css/main.css" type="text/css">
        <script src="./static/js/jquery.min.js" type="text/javascript"></script>
        
        <script type="text/javascript">
        var id = "${movie.id}";
        
        //轮询法更新影票
        function updateNumber()
        {
            $(document).ready(function() {
                $.ajax({
                    type: "GET",
                    url: "get-movie-number?id="+id,
                    success: function(resp) {
                    	var data = eval("("+resp+")");
                    	$("#movie-number-div").text("影票剩余："+data.number+"张");
                    	$("#movie-number").val(data.number);
                    }
                });
            });
        }
        setInterval('updateNumber()',3000);
        
        
        $(window).load(function(){
            var winPos = 1;
            var bgVal='';
            $(window).scroll(function(event){
                 winPos = $(window).scrollTop();
                 bgVal = 'rgba(0, 0, 0,+'+winPos/100+')';
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
        
        
        <div class="movie-content">
           <div class="movie-up">
                <div class="movie-left">
                    <img src="${movie.path}" height="380">
                </div>
                <div class="movie-mid">
                    <ul>
                        <li class="movie-title">${movie.name}</li>
                        <li>导演: ${movie.director}</li>
                        <li>编剧: ${movie.scriptwriter}</li>
                        <li>主演: ${movie.starring}</li>
                        <li>类型: ${movie.type}</li>
                        <li>制片国家/地区: ${movie.from}</li>
                        <li>语言: ${movie.language}</li>
                        <li>上映日期: ${movie.releaseDate}</li>
                        <li>片长: ${movie.time}</li>
                    </ul>
                </div>
                
                <div class="movie-right">
                    <div>特惠价格：${movie.price}元/张</div>
                    <div id="movie-number-div">影票剩余：${movie.number}张</div>
                    <input id="movie-number" value="${movie.number}" type="hidden">
                    <div><a id="GTBtn" href="#" onclick="getTicket(${movie.id})">立即抢购</a></div>
                </div>
            </div>
            
            <div class="movie-down">
                <div>
                    <h2>${movie.name}剧情介绍</h2>
                    <P>${movie.introduction}</P>
                     <h2>${movie.name}剧照</h2>
                    <img src="img/movie_1_1.jpg">
                    <img src="img/movie_1_2.jpg">
                    <img src="img/movie_1_3.jpg">
                                    
                </div>
            </div>
        </div>
    
        <div class="footer">
            Copyright © 2016-12-17 中信信用卡 aring Email：aringlai@163.com
        </div>
        
        <!-- <div class="pop-div">
        	<span>邮箱：</span><input type="text"><button>提交</button>
        </div> -->
        
        
        
        <script type="text/javascript">   	
 			var hasResult = false;	
        
        	//抢购影票
        	function  getTicket(id) {
        		hasResult = false;
        		var number = $("#movie-number").val();
				if(number<0) {
					alert("对不起，票余额不足");
				}else{
					$.ajax({
						url:"movie-GT?id="+id,
						success:function(resp){
							var data = eval("("+resp+")");
							if(data.success){
								getResult(id);					 
   						 	}else{
   						 	 alert(data.msg);
   						 	}	
						},
						error:function(){
							alert("服务器异常");
						},
					});
				}
			}
        	
        	function getResult(id){
        		var timer = setInterval( 
        	    	function (){
        	    		$.ajax({
        					url:"get-order?id="+id,
        					success:function(resp){
        						 var data = eval("("+resp+")");
        						 if(data.success){
        							 alert(data.msg);
            						 clearInterval(timer);  							 
        						 }
        					},
        					error:function(){
        						alert("服务器异常");
        						clearInterval(timer);  
        					},
        					}); 
        	    	},1000);
        		
        	}
        	
        
        
        
        </script>
        
        
        
    </body>

</html>