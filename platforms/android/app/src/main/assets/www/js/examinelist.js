$(function(){
  $('#examinetab').find('a').on('opened.tabs.amui',function(e){
    myScroll.destroy();
    load();
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

        dodeal("1","同意");
  });

  //点击拒绝
  $('.refuseBtn').off().on('touchend',function(e){
        var reason = $("#reason").val();
        dodeal("2",reason);
        $('.examineCon').eq(0).addClass('hide');
        $('.examineCon').eq(1).removeClass('hide');
  });

  //拒绝点击确定
  $('.sureBtn').off().on('touchend',function(e){
        setTimeout(function(){
            $('.examineCon').eq(1).addClass('hide');
            $('.examineCon').eq(0).removeClass('hide');
        },500);
        $('.upshadow').fadeOut();
        $('.upBox').stop().animate({bottom: -$('.upBox').height()}, 400);
  });
  
});
/**
 * @description load 下拉刷新 加载更多
 */
function load () {
  var pullDown = $(".pullDown"),
      pullUp = $(".pullUp"),
      pullDownLabel = $(".pullDownLabel"),
      pullUpLabel = $(".pullUpLabel"),
      container = $('.billCon.am-active'),
      loadingStep = 0;//加载状态0默认，1显示加载状态，2执行加载数据，只有当为0时才能再次加载，这是防止过快拉动刷新

  pullDown.hide();
  pullUp.hide();

  myScroll = new IScroll("#wrapper", {
    click:true,
    tabs:true,
    scrollbars: true,
    mouseWheel: false,
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
   * @description pullDownAction 下拉刷新
   */
  function pullDownAction(){
    setTimeout(function(){
      container.html('');
      var listli = '';
      var status = '';
      $.ajax({
        url:'json/listexaminlist.json',
        dataType:'json',
        success : function (data) {
          $.each(data.data,function(i,item){
            if(item.status == '待审核'){
              status = 'yellow';
            }else{
              status = '';
            }
            listli+='<li class="examineList '+status+'">'
                +'<div class="examineTitle am-cf">'
                +'<span class="am-fl titleLeft">'+item.name+'</span>'
                +'<span class="am-fr titleRight">'+item.time+'</span>'
                +'</div>'
                +'<div class="examineType am-cf">'
                +'<label class="am-fl">卡号：</label>'
                +'<span class="am-fl numberColor">'+item.cardNum+'</span>'
                +'<span class="am-fr examineStaus">'+item.status+'</span>'
                +'</div>'
                +'</li>';
          });
          container.append(listli);
          pullDown.attr('class','pullDown').hide();
          myScroll.refresh();
          loadingStep = 0;
        }
      });

    },1000);
  }

  /**
   * @description pullUpAction 加载更多
   */
  function pullUpAction(){
    setTimeout(function(){
      var listli = '';
      var status = '';
      $.ajax({
        url:'json/listexaminlist.json',
        dataType:'json',
        success : function (data) {
          $.each(data.data,function(i,item){
            if(item.status == '待审核'){
              status = 'yellow';
            }else{
              status = '';
            }
            listli+='<li class="examineList '+status+'">'
                +'<div class="examineTitle am-cf">'
                +'<span class="am-fl titleLeft">'+item.name+'</span>'
                +'<span class="am-fr titleRight">'+item.time+'</span>'
                +'</div>'
                +'<div class="examineType am-cf">'
                +'<label class="am-fl">卡号：</label>'
                +'<span class="am-fl numberColor">'+item.cardNum+'</span>'
                +'<span class="am-fr examineStaus">'+item.status+'</span>'
                +'</div>'
                +'</li>';
          });
          container.append(listli);
          pullUp.attr('class','pullUp').hide();
          myScroll.refresh();
          loadingStep = 0;
        }
      });

    },1000);
  }

  document.addEventListener('touchmove', function (e) { e.preventDefault(); }, isPassive() ? {
    capture: false,
    passive: false
  } : false);
}


/**
 * document.ready不能立马执行cordova.exec
 */
document.addEventListener('deviceready', function () {
     getApplyRecord();

}, false);


function  getApplyRecord(){
   cordova.exec(success, fail, "httpRequest", "APPLYRECORD", []);
}

function success(msg){
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

       record  = record + '<li class="examineList yellow"><div class="examineTitle am-cf">'
             +'<span class="am-fl titleLeft">'+ applyType +'</span>'
             +'<span class="am-fr titleRight">'+ applyDate +'</span></div>'
             +'<div class="examineType am-cf">'
             +'<label class="am-fl">卡号：</label>'
             +'<span class="am-fl numberColor">'+ cardId +'</span>'
             +'<span class="am-fr examineStaus">'+ applyState +'</span>'
             + '<input type="hidden" name="userid" value="' + userInnerId + '">'
             + '<input type="hidden" name="applyid" value="' + applyInnerId + '">'
             + '<input type="hidden" name="cardid" value="' + cardId + '">'
             + '<input type="hidden" name="status" value="' + applyState + '">'
             + '<input type="hidden" name="appdate" value="' + applyDate + '">'
             + '<input type="hidden" name="type" value="' + applyType + '">'
             +'</div></li>';
   }
   $("#records").append(record);
}

function fail(msg){
    var err = JSON.parse(msg);
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
    cordova.exec(succeed, failed, "httpRequest", "MESSAGEDEAL", [userid,applyid,i,j]);
}

function succeed(msg){
    window.location.href="examinelist.html";
}

function failed(msg){
    var err = JSON.parse(msg);
    var code = err.get("code");
    var msg = err.get("msg");
    if(code === '404' || code === '405'){
        alert(err.get("msg"));
        window.location.href="index.html";
    }else{
        alert(err.get("msg"));
    }
}
