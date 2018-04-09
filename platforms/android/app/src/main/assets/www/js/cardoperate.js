$(document).ready(function(){
    $("#frezenCard").click(function(){ //冻结请求
        cordova.exec(success, fail, "httpRequest", "FREEZECARD", []);
    });

    $("#unfreezeInfo").click(function(){ //解冻申请
           cordova.exec(success, fail, "httpRequest", "UNFREEZEINFO", []);
     });
});


function success(msg){
	alert(msg);
	window.location.href="cardoperate.html";
}

function fail(msg){
    var code = msg.code;
    var msg = msg.msg;
    if(code === 404 || code === 405){
        alert(msg);
        window.location.href="home.html";
    }else{
       alert(msg);
       window.location.href="cardoperate.html";
    }
}

/**
 * document.ready不能立马执行cordova.exec
 */
document.addEventListener('deviceready', function () {
     getCardInfo();

}, false);


function getCardInfo(){
    cordova.exec(succeed, fails, "httpRequest", "CARDINFO", []);
}

 function succeed(msg){
    var sucee =  JSON.parse(JSON.stringify(msg));
    var cardId = sucee.cardId;
    var accountState = sucee.accountState;
    $("#cardid").text(cardId);
    if(accountState === 0){
        $("#status").text("正常");
        $("#frezenCard").css("display","block");
    }
    else if(accountState === 1){
        $("#status").text("冻结");
        $("#unfreezeInfo").css("display","block");
    }
    else if(accountState === 2){
        $("#status").text("无账户信息");
    }
    else if(accountState === 3){
        $("#status").text("已过期");
    }
 }

function fails(msg){
    alert(msg);
}


