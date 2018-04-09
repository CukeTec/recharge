

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
   cordova.exec(success, fail, "httpRequest", "CARDINFO",[]);
}

//获取卡信息成功
function success(msg){
   var rechargeRatio = msg.rechargeRatio;
   var cardId = msg.cardId;
   var cardTime = msg.endTime;
   var accountMoney = msg.accountMoney;
   var cardType = msg.cardType;
   //alert("rechargeRatio:"+rechargeRatio+"cardId:"+cardId);
    $("#cardType").text(cardType);
   $("#cardId").text(cardId);
   $("#cardTime").text(cardTime);
   $("#accountMoney").text(accountMoney);
   $("#rechargeRatio").text(rechargeRatio);
}

function fail(msg){

	weakdialg(fail);
}