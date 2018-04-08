$(document).ready(function(){

    $("#makeSureBtn").click(function(){
        var amt = $("#amount").val();//充值金额
        var cardno = $("#cardno").val(); //卡号
        var pay = $("input[name='pay']:checked").val();//充值方式 0-微信  1-支付宝
        if(pay === '0'){
            alert("微信支付正在努力开发中....");

            return true;
        }

        //判断钱是否大于0
        var regPos = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/; //非负浮点数
        if(!regPos.test(amt)){
            alert("充值金额输入不正确");

            return true;
        }

        var amount = parseFloat(amt);
        if(amount < 1){
            alert("充值金额必须大于0元");

            return true;
        }

        cordova.exec(success, fail, "httpRequest", "RECHARGE", [amount,pay,cardno]);
    });
});


function success(msg){
	alert(msg);
}

function fail(msg){
    var code = msg.code;
    var msg = msg.msg;
    if(code === 404 || code === 405){
        alert(msg);
        window.location.href="index.html";
    }else{
        alert(msg);
    }
}