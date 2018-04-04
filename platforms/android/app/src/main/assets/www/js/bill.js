/**
 * document.ready不能立马执行cordova.exec
 */
document.addEventListener('deviceready', function () {
     getBillInfo();

}, false);

function  getBillInfo(){
   cordova.exec(succeed, fail, "httpRequest", "COMSUMTIONACTION", []);
}


function succeed(msg){
    var jsonObj = JSON.parse(msg);
    var bills = "";
    for(var p in jsonObj){//遍历json数组时，这么写p为索引，0,1
       var conDate = jsonObj[p].conDate; // 交易时间
       var userInnerId = jsonObj[p].userInnerId; // 人员id
       var conType = jsonObj[p].conType; //交易地点
       var money = jsonObj[p].money; // 交易金额
       var cardId = jsonObj[p].cardId; //卡号
       var conNumber = jsonObj[p].conNumber; //交易流水号

       bills  = bills + '<div class="billList"><div class="billTitle am-cf"><span class="am-fl titleLeft">' + conType + '</span><span class="am-fr titleRight">' + conDate + '</span></div><div class="billMoneyTitle">金额</div><div class="billMoney"><em>￥</em>' + money + '</div><div class="billType am-cf"><label class="am-fl">消费种类：</label><span class="am-fl">刷卡消费</span></div></div>';
    }
     $("#total_bill").append(bills);

}

function fail(msg){
    var err = JSON.stringify(msg);
  /*  alert(err);
    var code = err.get("code");
    var msg = err.get("msg");
    if(code === '404' || code === '405'){
        alert(err.get("msg"));
        window.location.href="index.html";
    }else{
        alert(err.get("msg"));
    }*/
}

//点击事件
$(document).ready(function(){

    //消费记录
    $("#billBtn").click(function(){

         getBillInfo();
    });

    //充值记录
    $("#rechargeBtn").click(function(){
        getRechargeInfo();
    });
});

//充值记录事件
function  getRechargeInfo(){
   cordova.exec(success, failed, "httpRequest", "RECHARGERECORD", []);
}


//充值记录成功
function success(msg){
    var jsonObj = JSON.parse(msg);
    alert(jsonObj);

    /**
    var bills = "";
    for(var p in jsonObj){//遍历json数组时，这么写p为索引，0,1
       var conDate = jsonObj[p].conDate; // 交易时间
       var userInnerId = jsonObj[p].userInnerId; // 人员id
       var conType = jsonObj[p].conType; //交易地点
       var money = jsonObj[p].money; // 交易金额
       var cardId = jsonObj[p].cardId; //卡号
       var conNumber = jsonObj[p].conNumber; //交易流水号

       bills  = bills + '<div class="billList"><div class="billTitle am-cf"><span class="am-fl titleLeft">' + conType + '</span><span class="am-fr titleRight">' + conDate + '</span></div><div class="billMoneyTitle">金额</div><div class="billMoney"><em>￥</em>' + money + '</div><div class="billType am-cf"><label class="am-fl">消费种类：</label><span class="am-fl">刷卡消费</span></div></div>';
    }
     $("#total_bill").append(bills);
    **/
}

function failed(msg){
    alert(msg);
   /* var code = msg.get("code");
    var msg = msg.get("msg");
    if(code === 404 || code === 405){
        alert(msg.get("msg"));
        window.location.href="index.html";
    }else{
        alert(msg.get("msg"));
    }*/
}
