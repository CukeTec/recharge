

/**
 * document.ready不能立马执行cordova.exec
 */
document.addEventListener('deviceready', function () {
     fixCardInfo();

}, false);

/**
 * 填充卡信息
 */
function fixCardInfo(){
   cordova.exec(success, fail, "httpRequest", "cardInfo", ["http://sireyun.com:8081/PSMGABService/cardInfo"]);
}

//获取卡信息成功
function success(msg){

   alert(msg);
}

function fail(msg){

	alert(fail);
}