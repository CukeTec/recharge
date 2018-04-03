/**
 * document.ready不能立马执行cordova.exec
 */
document.addEventListener('deviceready', function () {
     getBillInfo();

}, false);

function  getBillInfo(){
   cordova.exec(succeed, fails, "httpRequest", "COMSUMTIONACTION", []);
}

var billId = [];

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

function fails(msg){
    var code = err.get("code");
    var msg = err.get("msg");
    if(code === '404' || code === '405'){
        alert(err.get("msg"));
        window.location.href="index.html";
    }else{
        alert(err.get("msg"));
    }
}


