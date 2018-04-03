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

$("#setquestion").click(function(){
    cordova.exec(success1, fail1, "httpRequest", "GETQUESTION",[]);
});
function success1(msg){
    var questions = JSON.parse(msg);
    if(questions.length == 3){
         window.location.href = "valquestion.html";
    }else{
         window.location.href= "setting.html";
    }
}
function fail1(){

}
