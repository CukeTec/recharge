$(document).ready(function(){

    $("#makeSureBtn").click(function(){
        var amt = $("#amount").val();//充值金额
        var cardno = $("#cardno").val(); //卡号
        var pay = $("input[name='pay']:checked").val();//充值方式 0-微信  1-支付宝
        if(pay === '0'){
           dialg("微信支付正在努力开发中....");

           return true;
        }

        //判断钱是否大于0
        var regPos = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/; //非负浮点数
        if(!regPos.test(amt)){
            dialg("充值金额格式不正确");

            return true;
        }

        var amount = parseFloat(amt);
        if(amount < 1){
            dialg("充值金额必须大于0元");

            return true;
        }

        cordova.exec(success, fail, "httpRequest", "RECHARGE", [amount,pay,cardno]);
    });
});


function success(msg){
	dialg(msg);
	window.location.href="refill.html";
}

function fail(msg){
    var code = msg.code;
    var msg = msg.msg;
    if(code === '404' || code === '405'){
        dialg(msg);
        window.location.href="home.html";
    }else{
        dialg(msg);
    }
}


//初始化
document.addEventListener('deviceready', function () {
    getCardno();
}, false);

function getCardno(){
    cordova.exec(succ, error, "httpRequest", "RECHARGEPRE", []);
}

function succ(msg){
    $("#cardId").text(msg);
    $("#cardno").val(msg);
}

function error(msg){
    alert("查询不到卡号");
}