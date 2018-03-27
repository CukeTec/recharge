

$(document).ready(function(){
	login();
	initpage();
});

/**
 * rem布局 
 * 
 */
function initpage()  
  {  
    var view_width = document.getElementsByTagName('html')[0].getBoundingClientRect().width;  
    var _html = document.getElementsByTagName('html')[0];  
    view_width>640?_html.style.fontSize=640/16 +'px':_html.style.fontSize =view_width/16+'px';
 }
  



/**
 * 模拟登陆 
 */
function login(){
	cordova.exec(success, fail, "httpRequest", "login", ["http://sireyun.com:8081/PSMGABService/loginAuth",
	"469747","E10ADC3949BA59ABBE56E057F20F883E"]);
}

function success(msg){
	alert(msg);
}

function fail(msg){
	alert(fail);
}
