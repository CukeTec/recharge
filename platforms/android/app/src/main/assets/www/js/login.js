$(document).ready(function(){

    $("#loginBtn").click(function(){

         cordova.exec(success, fail, "httpRequest", "login", ["http://sireyun.com:8081/PSMGABService/loginAuth",
         	"469747","123456"]);
    });
});

//登录成功
function success(msg){
	window.location.href="index.html";
}

function fail(msg){
	alert(fail);
}