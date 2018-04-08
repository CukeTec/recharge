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
        getRechargeInfo(1,10);
    });
});

//充值记录事件
function  getRechargeInfo(currpage,pagesize){
   cordova.exec(success, failed, "httpRequest", "RECHARGERECORD", [currpage,pagesize]);
}


//充值记录成功
function success(msg){
    alert(msg);
    var jsonObj = JSON.parse(msg);
    var jsonobject = jsonObj.nameValuePairs;
    alert("2:"+JSON.stringify(jsonobject));

    var total = jsonobject.total;
    var currpage = jsonobject.currpage;
    var totalpage = jsonobject.totalpage;
    var result = jsonobject.result;

    alert("total:"+total);
    alert("currpage:"+currpage);
    alert("totalpage:"+totalpage);

    var bills = "";
    for(var p in result){//遍历json数组时，这么写p为索引，0,1
        var AccountDate = result[p].AccountDate; // 充值时间
        var userInnerId = result[p].userInnerId; // 人员id
        // 0：平台有卡 1：平台无卡 2：安卓app 3：IOSapp 4：微信app 5：现金圈存机6：银行圈存机
        var RechargeType = result[p].RechargeType; //充值方式
        var Money = result[p].Money; // 金额
        var AccountTypeName = result[p].AccountTypeName; //卡号
        var StreamCode = result[p].StreamCode; //流水号

        bills  = bills + '<div class="billList"><div class="billTitle am-cf"><span class="am-fl titleLeft">' + conType + '</span><span class="am-fr titleRight">' + conDate + '</span></div><div class="billMoneyTitle">金额</div><div class="billMoney"><em>￥</em>' + money + '</div><div class="billType am-cf"><label class="am-fl">消费种类：</label><span class="am-fl">刷卡消费</span></div></div>';
    }

    $("#total_bill").append(bills);

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
