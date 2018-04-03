
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
  document.addEventListener('touchmove', function (e) { e.preventDefault(); }, isPassive() ? {
    capture: false,
    passive: false
  } : false);
}
/**
 * isPassive 防止滚动条卡顿
 * @returns {boolean}
 */
function isPassive() {
  var supportsPassiveOption = false;
  try {
    addEventListener("test", null, Object.defineProperty({}, 'passive', {
      get: function () {
        supportsPassiveOption = true;
      }
    }));
  } catch(e) {}
  return supportsPassiveOption;
}



