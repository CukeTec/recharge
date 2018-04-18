

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
   var data = JSON.parse(msg);
   var rechargeRatio = data.rechargeRatio;
   var cardId = data.cardId;
   var cardTime = data.endTime;
   var accountMoney = data.accountMoney;
   var cardType = data.cardType;
   //alert("rechargeRatio:"+rechargeRatio+"cardId:"+cardId);
    $("#cardType").text(cardType);
   $("#cardId").text(cardId);
   $("#cardTime").text(cardTime);
   $("#accountMoney").text(accountMoney);
   $("#rechargeRatio").text(rechargeRatio);
}

function fail(msg){

	    var data = JSON.parse(msg);
        weakdialg(data.msg);
        if(data.code == "405"){
            window.location.href = "logintext.html";
        }
}