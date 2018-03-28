var messageId = [];
document.addEventListener('deviceready', function () {
     var url = document.URL;
     var id = url.split("?")[1];
     cordova.exec(success, fail, "httpRequest", "msgDetail", ["http://sireyun.com:8081/PSMGABService/messageDetail", id]);
}, false);
function success(msg){
    var message = JSON.parse(msg);
    var detail = '<img src="images/listicon_1.png" alt=""/><span class="listiconText">香心洗涤</span><h3 class="am-list-item-hd">'
                   + message[0].messageName + '</h3><span class="page">'
                   + message[0].messageDetail + '</span><div class="timeBox"><label class="left">时间：<span>'
                   + message[0].sendDate + '</span></label><label class="left">发件人：<span>'
                   + message[0].sendUser + '</span></label></div>';
    $(".msgDetail").append(detail);
}
function fail(msg){
    return;
}
