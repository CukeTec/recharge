var messageId = [];
var page = 1;
var row = 10;
document.addEventListener('deviceready', function () {
     more(page, row);
}, false);
function more(p, r){
    cordova.exec(success, fail, "httpRequest", "GETMESSAGE", [p, r]);
}
function success(msg){
    var result = JSON.parse(msg);
    if(result.length == row){
        page++;
    }
    var message;
    $.each(result, function(i,v){
        console.log($.inArray(v.messageInnerId, messageId))
        if($.inArray(v.messageInnerId, messageId) == -1){
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
        }
    });
    load();
}
function fail(msg){
        var data = JSON.parse(msg);
        weakdialg(data.msg);
        if(data.code == "405"){
            window.location.href = "logintext.html";
        }
}
function jumpto(msg){
    window.location.href="msg.html";
    return;
}
$("#clear").click(function(){
    cordova.exec(jumpto, fail, "httpRequest", "DELETEMESSAGE", []);
})
