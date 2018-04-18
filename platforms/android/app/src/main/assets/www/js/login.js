$(document).ready(function(){

    $("#loginBtn").click(function(){
        var userId = $("#userId").val(); //用户
        var userPassword = $("#userPassword").val(); //密码
        if(userId == null || userId == "" || userPassword == null || userPassword == "" ){
             dialg("用户名或密码不能为空");

             return true;
        }

         cordova.exec(success, fail, "httpRequest", "LOGIN", [userId,userPassword]);
    });

    $("#forget").click(function(){
           window.location.href="setting.html";

    });
});

//登录成功
function success(msg){
	window.location.href="index.html";
}

function fail(msg){
    weakdialg(msg);
}