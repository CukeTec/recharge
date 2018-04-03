$(function(){
  $('#examinetab').find('a').on('opened.tabs.amui',function(e){
    myScroll.scrollTo(0,0,100);
  });
  //底下悬浮框的显示与隐藏
  $('.yellow .examineStaus').die().live('touchend',function(e){
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
  });
  //点击拒绝
  $('.refuseBtn').off().on('touchend',function(e){
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