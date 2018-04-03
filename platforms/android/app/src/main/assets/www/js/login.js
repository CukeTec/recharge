$(document).ready(function(){

    $("#loginBtn").click(function(){

         cordova.exec(success, fail, "httpRequest", "LOGIN", ["469747","123456"]);
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
	alert(fail);
}