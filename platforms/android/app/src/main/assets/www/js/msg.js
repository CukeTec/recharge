var messageId = [];
document.addEventListener('deviceready', function () {
     cordova.exec(success, fail, "httpRequest", "GETMESSAGE", [1, 10]);
}, false);
function success(msg){
    var message;
    $.each(JSON.parse(msg), function(i,v){
         messageId.push(v.messageInnerId);
         message = '<li class="am-g am-list-item-desced am-list-item-thumb am-list-item-thumb-left">'
                       + '<a href="msgdetail.html?' + v.messageInnerId + '" class="" style="color:black"><div class="am-u-sm-4 am-list-thumb msgListH relative">'
                       + '<img src="images/listicon_1.png" alt="ddd"/>'
                       + '<span class="listiconText">'+ v.sendUser + '</span></div>'
                       + '<div class="am-u-sm-8 am-list-main msgListH"><h3 class="am-list-item-hd">维护通知</h3>'
                       + '<div class="am-list-item-text listCon">' + v.messageName
                       + '</div><div class="am-list-item-text listTime">' + v.sendDate
                       + '</div></div></a></li>';
         $(".am-list").append(message);
    });
}
function fail(msg){
    return;
}
function jumpto(msg){
    window.location.href="msg.html";
    return;
}
$("#clear").click(function(){
    cordova.exec(jumpto, fail, "httpRequest", "DELETEMESSAGE", []);
})
