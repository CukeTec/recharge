
$(function () {
  //rem布局
  initpage();
});

/**
 * rem布局 
 * 
 */

function initpage()  
  {  
    var view_width = document.getElementsByTagName('html')[0].getBoundingClientRect().width;  
    var _html = document.getElementsByTagName('html')[0];
    view_width>640?_html.style.fontSize=640/16 +'px':_html.style.fontSize =view_width/16+'px';
  }
var myScroll;
function iscroll(){
  //平滑滚动条
  myScroll = new IScroll('#wrapper',{
    click:true,
    taps:true,
    mouseWheel:true,
    scrollbars: true
  });
}

