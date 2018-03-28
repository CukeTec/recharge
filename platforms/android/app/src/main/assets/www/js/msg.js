document.addEventListener('deviceready', function () {
     cordova.exec(success, fail, "httpRequest", "msg", ["http://sireyun.com:8081/PSMGABService/messageRecord"]);
}, false);
function success(msg){
    var message;
    $.each(JSON.parse(msg), function(i,v){
         message = '<li class="am-g am-list-item-desced am-list-item-thumb am-list-item-thumb-left" value=' + v.messageInnerId + '>'
                       + '<div class="am-u-sm-4 am-list-thumb msgListH relative">'
                       + '<a href="msgdetail.html" class=""><img src="images/listicon_1.png" alt="ddd"/></a>'
                       + '<span class="listiconText">'+ v.sendUser + '</span></div>'
                       + '<div class="am-u-sm-8 am-list-main msgListH"><h3 class="am-list-item-hd">维护通知</h3>'
                       + '<div class="am-list-item-text listCon">' + v.messageName
                       + '</div><div class="am-list-item-text listTime">' + v.sendDate
                       + '</div></div></li>';
         $(".am-list").append(message);
    });
}
function fail(msg){
    alert(msg);
}
