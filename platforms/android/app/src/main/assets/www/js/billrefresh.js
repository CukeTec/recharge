$(document).ready(function(){
	
    $('#billtab').find('a').on('opened.tabs.amui',function(e){
        myScroll.destroy();
        load();
    });
    
    $(".am-tabs-nav>li").click(function(){
		$(".am-tabs-nav>li").removeClass("am-active");
		$(this).addClass("am-active");
		var index = $(this).index();
		if(index == 0) { //消费
        	$("#records").empty();
        	getConsumBill(1,10);
        }
        else if(index == 1) { //充值
        	$("#records").empty();
        	getRechargeBill(1,10);
        }
		/*
		if(index == 0) { //全部
		    $("#records").empty();
			getBill();
		} 
		else if(index == 1) { //消费
		   $("#records").empty();
			getConsumBill(1,10);
		} 
		else if(index == 2) { //充值
		   $("#records").empty();
			getRechargeBill(1,10);
		}
		*/
		
	});
    
});
/**
 * @description load 下拉刷新 加载更多
 */
function load() {
    var pullDown = $(".pullDown"),
        pullUp = $(".pullUp"),
        pullDownLabel = $(".pullDownLabel"),
        pullUpLabel = $(".pullUpLabel"),
        container = $('.billCon.am-active'),
        loadingStep = 0;//加载状态0默认，1显示加载状态，2执行加载数据，只有当为0时才能再次加载，这是防止过快拉动刷新

    pullDown.hide();
    pullUp.hide();

    myScroll = new IScroll("#wrapper", {
        scrollbars: true,
        mouseWheel: false,
        click:true,
        tabs:true,
        interactiveScrollbars: true,
        shrinkScrollbars: 'scale',
        fadeScrollbars: true,
        scrollY:true,
        probeType: 2,
        bindToWrapper:true
    });
    myScroll.on("scroll",function(){
        if(loadingStep == 0 && !pullDown.attr("class").match('refresh|loading') && !pullUp.attr("class").match('refresh')){
            if(this.y > 40){//下拉刷新操作
                pullDown.addClass("refresh").show();
                pullDownLabel.text("松手刷新数据");
                loadingStep = 1;
                myScroll.refresh();
            }else if(this.y < (this.maxScrollY - 14)){//上拉加载更多
                pullUp.addClass("refresh").show();
                pullUpLabel.text("正在载入");
                loadingStep = 1;
                pullUpAction();
            }
        }
    });
    myScroll.on("scrollEnd",function(){
        if(loadingStep == 1){
            if( pullDown.attr("class").match("refresh") ){//下拉刷新操作
                pullDown.removeClass("refresh").addClass("loading");
                pullDownLabel.text("正在刷新");
                loadingStep = 2;
                pullDownAction();
            }
        }
    });
    /**
     * @description pullDownAction下拉刷新
     */
    function pullDownAction(){
         $("#records").empty();
        var type = $("#type").val(); //查看内容  0：全部  1：消费记录  2：充值记录
        setTimeout(function(){
            if(type === 0){  //查看内容  0：全部  1：消费记录  2：充值记录
               getConsumBill(1,10);
            }else if(type === 1){ //  1：消费记录
               getRechargeBill(1,10);
            }else{ // 2：充值记录
               getRechargeBill(1,10);
            }

            pullDown.attr('class','pullDown').hide();
            myScroll.refresh();
            loadingStep = 0;

        },1000);
    }

    /**
     * @description pullUpAction 加载更多
     */
    function pullUpAction(){
    	var currpage = $("#currpage").val(); //当前页
    	var pagesize = $("#pagesize").val(); //每页条数
    	var totalpage = $("#totalpage").val(); //总共页数
    	var type = $("#type").val(); //查看内容  0：全部  1：消费记录  2：充值记录
    	if(currpage === totalpage){

            return true;
    	}
    	var nextpage = parseFloat(currpage) + 1;

        setTimeout(function(){

            if(type === 0){  //查看内容  0：全部  1：消费记录  2：充值记录
                getConsumBill(nextpage,pagesize);
            }else if(type === 1){ //  1：消费记录
                getRechargeBill(nextpage,pagesize);
            }else{ // 2：充值记录
                getRechargeBill(nextpage,pagesize);
            }
            pullUp.attr('class','pullUp').hide();
            myScroll.refresh();
            loadingStep = 0;
                
        },1000);
    }

    document.addEventListener('touchmove', function (e) { e.preventDefault(); }, isPassive() ? {
        capture: false,
        passive: false
    } : false);
}

//消费
function getConsumBill(currpage,pagesize){
   cordova.exec(succeed, fail, "httpRequest", "COMSUMTIONACTION", [currpage,pagesize]);
}


function succeed(msg){
    var jsonObj = JSON.parse(msg);
    var jsonobject = jsonObj.nameValuePairs;

    var total = jsonobject.total;
    var currpage = jsonobject.currpage;
    var totalpage = jsonobject.totalpage;
    var pagesize = jsonobject.pagesize;
    var result = jsonobject.result;
    var bills = "";
    if(result.length > 0){
        for(var p in result){//遍历json数组时，这么写p为索引，0,1
           var conDate = result[p].conDate; // 交易时间
           var userInnerId = result[p].userInnerId; // 人员id
           var conType = result[p].conType; //交易地点
           var money = result[p].money; // 交易金额
           var cardId = result[p].cardId; //卡号
           var conNumber = result[p].conNumber; //交易流水号

           bills  = bills + '<li class="billList"><div class="billTitle am-cf"><span class="am-fl titleLeft">' + conType + '</span>'
                          +'<span class="am-fr titleRight">' + conDate + '</span></div>'
                          +'<div class="billMoneyTitle">金额</div><div class="billMoney"><em>￥</em>-' + money + '</div>'
                          +'<div class="billType am-cf"><label class="am-fl">消费种类：</label>'
                          +'<span class="am-fl">刷卡消费</span></div></li>';

        }
     }else{
        bills = "无消费记录";
     }

    $("#currpage").val(currpage); //当前页
    $("#pagesize").val(pagesize); //每页条数
    $("#totalpage").val(totalpage); //总共页数
    $("#type").val(1); //查看内容  0：全部  1：消费记录  2：充值记录

    $("#records").append(bills);
    load();
}

function fail(msg){
   var code = msg.code;
   var msg = msg.msg;
   if(code === '404' || code === '405'){
      alert(msg);
      window.location.href="home.html";
   }else{
      alert(msg);
   }
}




//充值记录
function getRechargeBill(currpage,pagesize){
	 cordova.exec(success, failed, "httpRequest", "RECHARGERECORD", [currpage,pagesize]);
}

//充值记录成功
function success(msg){
    var jsonObj = JSON.parse(msg);
    var jsonobject = jsonObj.nameValuePairs;

    var total = jsonobject.total;
    var currpage = jsonobject.currpage;
    var totalpage = jsonobject.totalpage;
    var pagesize = jsonobject.pagesize;
    var result = jsonobject.result;
    var bills = "";
    for(var p in result){//遍历json数组时，这么写p为索引，0,1
        var AccountDate = result[p].AccountDate; // 充值时间
        var userInnerId = result[p].userInnerId; // 人员id
        // 0：平台有卡 1：平台无卡 2：安卓app 3：IOSapp 4：微信app 5：现金圈存机6：银行圈存机
        var RechargeType = result[p].RechargeType; //充值方式
        var Money = result[p].Money; // 金额
        var AccountTypeName = result[p].AccountTypeName; //卡号
        var StreamCode = result[p].StreamCode; //流水号

        var recharge = "";
        if(RechargeType === 0){
            recharge = "平台有卡";
        }else if(RechargeType === 1){
            recharge = "平台无卡";
        }else if(RechargeType === 2){
            recharge = "安卓app";
        }else if(RechargeType === 3){
            recharge = "IOS app";
        }else if(RechargeType === 4){
            recharge = "微信app";
        }else if(RechargeType === 5){
            recharge = "现金圈存机";
        }else if(RechargeType === 6){
            recharge = "银行圈存机";
        }else{
            recharge = RechargeType;
        }
       bills = bills + '<li class="billList"><div class="billTitle am-cf">'
             +'<span class="am-fl titleLeft">' + recharge + '</span>'
             +'<span class="am-fr titleRight">' + AccountDate + '</span></div>'
             +'<div class="billMoneyTitle">金额</div><div class="billMoney green"><em>￥</em>' + Money + '</div>'
             +'<div class="billType am-cf"><label class="am-fl">消费种类：</label>'
             +'<span class="am-fl">充值</span></div></li>';
    }

    $("#currpage").val(currpage); //当前页
    $("#pagesize").val(pagesize); //每页条数
    $("#totalpage").val(totalpage); //总共页数
    $("#type").val(2); //查看内容  0：全部  1：消费记录  2：充值记录

    $("#records").append(bills);
    load();
}

function failed(msg){
   var code = msg.code;
   var msg = msg.msg;
   if(code === '404' || code === '405'){
       alert(msg);
       window.location.href="home.html";
   }else{
       alert(msg);
   }
}

/**
 * document.ready不能立马执行cordova.exec   test
 */
document.addEventListener('deviceready', function () {
    $("#records").empty();
    getConsumBill(1,10);
}, false);





















