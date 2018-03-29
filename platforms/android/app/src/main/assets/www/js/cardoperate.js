$(document).ready(function(){

    $("#frezenCard").click(function(){ //冻结请求
        cordova.exec(success, fail, "httpRequest", "freezeCard", [""]);
    });

    $("#unfreezeInfo").click(function(){ //解冻申请
            cordova.exec(success, fail, "httpRequest", "unfreezeInfo", [""]);
     });

});

function success(msg){
	alert(msg);
}

function fail(msg){
    var err = JSON.stringify(msg);
    var code = err.get("code");
    var msg = err.get("msg")
    if(code === '404' || code === '405'){
        alert(err.get("msg"));
        window.location.href="index.html";
    }else{
        alert(err.get("msg"));
    }
}
