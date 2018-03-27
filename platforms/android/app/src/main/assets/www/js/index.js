$(document).ready(function(){

    fixCardInfo();
});

/**
 * 填充卡信息
 */
function fixCardInfo(){
   cordova.exec(success, fail, "httpRequest", "cardInfo", ["http://sireyun.com:8081/PSMGABService/cardInfo",
            	"469747"]);
}

//获取卡信息成功
function success(msg){

}

function fail(msg){
	alert(fail);
}