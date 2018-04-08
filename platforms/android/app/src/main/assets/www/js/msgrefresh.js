/*$(function(){
  $('.clearBtn').off().on('click',function(){
    $('#mymodal').modal('open');
    setTimeout(function(){
      $('#mymodal').modal('close');
    },5000);
  });
  $('.msgList li').die().live('click',function(){
    window.location.href='msgdetail.html';
  });
});*/
/**
 * @description load 下拉刷新 加载更多
 */
function load () {
    var myScroll,
        pullDown = $(".pullDown"),
        pullUp = $(".pullUp"),
        pullDownLabel = $(".pullDownLabel"),
        pullUpLabel = $(".pullUpLabel"),
        container = $('#thelist'),
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
            var listli = '';
            container.html('');
            $.ajax({
                url:'json/listmsg.json',
                dataType:'json',
                success : function (data) {
                    $.each(data.data,function(i,item){
                        listli+='<li class="am-g am-list-item-desced am-list-item-thumb am-list-item-thumb-left">'
                            +'<div class="am-u-sm-4 am-list-thumb msgListH relative">'
                            +'<a href="javascript:;" class="">'
                            +'<img src="'+item.img+'" alt="ddd"/>'
                            +'</a>'
                            +'<span class="listiconText">'+item.name+'</span>'
                            +'</div>'
                            +'<div class="am-u-sm-8 am-list-main msgListH">'
                            +'<h3 class="am-list-item-hd">'+item.title+'</h3>'
                            +'<div class="am-list-item-text listCon">'+item.content+'</div>'
                            +'<div class="am-list-item-text listTime">'+item.time+'</div>'
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
            $.ajax({
                url:'json/listmsg.json',
                dataType:'json',
                success : function (data) {
                    $.each(data.data,function(i,item){
                        listli+='<li class="am-g am-list-item-desced am-list-item-thumb am-list-item-thumb-left">'
                            +'<div class="am-u-sm-4 am-list-thumb msgListH relative">'
                            +'<a href="javascript:;" class="">'
                            +'<img src="'+item.img+'" alt="ddd"/>'
                            +'</a>'
                            +'<span class="listiconText">'+item.name+'</span>'
                            +'</div>'
                            +'<div class="am-u-sm-8 am-list-main msgListH">'
                            +'<h3 class="am-list-item-hd">'+item.title+'</h3>'
                            +'<div class="am-list-item-text listCon">'+item.content+'</div>'
                            +'<div class="am-list-item-text listTime">'+item.time+'</div>'
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