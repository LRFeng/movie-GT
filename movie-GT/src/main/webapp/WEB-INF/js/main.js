WB2.login(function() {
	
});


WB2.logout(function() {
	
});


WB2.checkLogin();

function weiboLogin(){
	$.ajax({
		url:"weibo-auth-login",
		success:function(resp){
			alter(resp);
		}
	});
	
}

function logout() {
	
}