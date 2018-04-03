$(function(){
  $('#examinetab').find('a').on('opened.tabs.amui',function(e){
    myScroll.scrollTo(0,0,100);
  });
  //底下悬浮框的显示与隐藏
  $('.yellow .examineStaus').die().live('touchend',function(e){
		var userid = $(this).parent().find("input[name='userid']").val();
		var applyid = $(this).parent().find("input[name='applyid']").val();
		var cardid = $(this).parent().find("input[name='cardid']").val();
		var status = $(this).parent().find("input[name='status']").val();
		var appdate = $(this).parent().find("input[name='appdate']").val();
		var type = $(this).parent().find("input[name='type']").val();

		$("#cardstatus").text(status);
        $("#applydate").text(appdate);
        $("#apply").text(cardid);
        $("#applystatus").text(type);

        $("#userid").val(userid);
        $("#applyid").val(applyid);

        $('.upshadow').fadeIn();
        $('.upBox').removeClass('hide');
        $('.upBox').stop().animate({bottom: '0'}, 500);
  });
  //点击阴影消失
  $('.upshadow').off().on('touchend',function(e){
    $('.upshadow').fadeOut();
    $('.upBox').stop().animate({bottom: -$('.upBox').height()}, 500);
  });
  //点击同意
  $('.agreeBtn').off().on('click',function(e){
    $('.upshadow').fadeOut();
    $('.upBox').stop().animate({bottom: -$('.upBox').height()}, 500);
    $('#mymodal').modal('open');
    setTimeout(function(){
      $('#mymodal').modal('close');
    },3000);

    //同意
    dodeal("1","同意");
  });
  //点击拒绝
  $('.refuseBtn').off().on('touchend',function(e){
    $('.examineCon').eq(0).addClass('hide');
    $('.examineCon').eq(1).removeClass('hide');
  });
  //拒绝点击确定
  $('.sureBtn').off().on('touchend',function(e){
    $('.examineCon').eq(1).addClass('hide');
    $('.examineCon').eq(0).removeClass('hide');
    $('.upshadow').fadeOut();
    $('.upBox').stop().animate({bottom: -$('.upBox').height()}, 500);
  });
});

/**
 * document.ready不能立马执行cordova.exec
 */
document.addEventListener('deviceready', function () {
     getApplyRecord();

}, false);

function  getApplyRecord(){
   cordova.exec(success, fail, "httpRequest", "applyrecordaction", []);
}

function success(msg){
    alert(msg);
    var jsonObj = JSON.parse(msg);
    var record = "";
    for(var p in jsonObj){
       var userInnerId = jsonObj[p].userInnerId; // 用户id
       var applyRemark = jsonObj[p].applyRemark; // 申请备注
       var cardId = jsonObj[p].cardId; //卡号
       var applyInnerId = jsonObj[p].applyInnerId; //申请id
       var applyState = jsonObj[p].applyState; // 审核状态
       var applyDate = jsonObj[p].applyDate; //申请日期
       var applyType = jsonObj[p].applyType; //申请类型
       
       record  = record +  '<div class="examineList yellow"><div class="examineTitle am-cf">'
          + '<span class="am-fl titleLeft">' + applyType + '</span>'
          + '<span class="am-fr titleRight">' + applyDate + '</span></div><div class="examineType am-cf">'
          + '<label class="am-fl">卡号：</label><span class="am-fl numberColor">' + cardId + '</span>'
          + '<span class="am-fr examineStaus">' + applyState + '</span>'
          + '<input type="hidden" name="userid" value="' + userInnerId + '">'
					+ '<input type="hidden" name="applyid" value="' + applyInnerId + '">'
					+ '<input type="hidden" name="cardid" value="' + cardId + '">'
					+ '<input type="hidden" name="status" value="' + applyState + '">'
					+ '<input type="hidden" name="appdate" value="' + applyDate + '">'
					+ '<input type="hidden" name="type" value="' + applyType + '"></div></div>';
    }
    $("#records").append(record);
    
}

function fail(msg){
    var code = err.get("code");
    var msg = err.get("msg");
    if(code === '404' || code === '405'){
        alert(err.get("msg"));
        window.location.href="index.html";
    }else{
        alert(err.get("msg"));
    }
}

//审核处理
function dodeal(i,j){
    var userid = $("#userid").val();
    var applyid = $("#applyid").val();
    cordova.exec(succeed, failed, "httpRequest", "messagedealaction", [userid,applyid,i,j]);
}

function succeed(msg){
    alert(msg);
    window.location.href="examinelist.html";
}

function failed(msg){
    var code = err.get("code");
    var msg = err.get("msg");
    if(code === '404' || code === '405'){
        alert(err.get("msg"));
        window.location.href="index.html";
    }else{
        alert(err.get("msg"));
    }
}
