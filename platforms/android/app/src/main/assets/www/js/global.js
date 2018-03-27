$(function () {
  //rem布局
  initpage();
  //平滑滚动条
  //var IScroll = $.AMUI.iScroll;
  //var myScroll = new IScroll('#wrapper',{
  //  click:true
  //});
});

/**
 * rem布局 
 * 
 */

function initpage()  
  {  
    var view_width = document.getElementsByTagName('html')[0].getBoundingClientRect().width;  
    var _html = document.getElementsByTagName('html')[0];  
    _html.style.fontSize =view_width/16+'px';
  }
