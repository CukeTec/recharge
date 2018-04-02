var billId = [];
document.addEventListener('deviceready', function () {
     bill();
}, false);

function bill(){
    alert(123);
    var startDate = "";
    var endDate = "";
    cordova.exec(success, fail, "httpRequest", "consumtionaction", [startDate,endDate]);
}

function success(msg){
    alert(msg);
     var bills;
     $.each(JSON.parse(msg), function(i,v){
         alert(v);
         billId.push(v.userInnerId);
         bills = '<div class="billList">'
                    +'<div class="billTitle am-cf">'
                    +'<span class="am-fl titleLeft">' + v.conType + '</span>'
                    +'<span class="am-fr titleRight">' + v.conDate + '</span>'
                    +'</div><div class="billMoneyTitle">金额</div><div class="billMoney"><em>￥</em>' + v.money + '</div>
                    +'<div class="billType am-cf"><label class="am-fl">消费种类：</label><span class="am-fl">'+刷卡消费+'</span></div></div>';
         $("#total_bill").append(bills);
     });
}

function fail(msg){
    var err = JSON.stringify(msg);
    var code = err.get("code");
    var msg = err.get("msg");
    if(code === '404' || code === '405'){
        alert(err.get("msg"));
        window.location.href="index.html";
    }else{
        alert(err.get("msg"));
    }
}