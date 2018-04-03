document.addEventListener('deviceready', function () {
     cordova.exec(success, fail, "httpRequest", "CARDINFO",[]);

}, false);

//获取卡信息成功
function success(msg){
   var cardId = msg.cardId;
   $("#cardId").text(cardId);
}

function fail(msg){
//	alert(fail);
}