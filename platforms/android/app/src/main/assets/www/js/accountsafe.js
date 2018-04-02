document.addEventListener('deviceready', function () {
     cordova.exec(success, fail, "httpRequest", "cardInfo", ["http://sireyun.com:8081/PSMGABService/cardInfo"]);

}, false);

//获取卡信息成功
function success(msg){
   var cardId = msg.cardId;
   $("#cardId").text(cardId);
}

function fail(msg){
//	alert(fail);
}