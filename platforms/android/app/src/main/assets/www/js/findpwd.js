var url = document.URL;
var account = url.split("?")[1];
$(".makeSureBtn").click(function(){
    var inputs = $("input[class='am-fl inputbg'");
    if($(inputs[0]).val().trim() == ""){
        alert("密码不可为空");
        return;
    }
    if($(inputs[0]).val() != $(inputs[1]).val()){
        alert("密码不一致");
        return;
    }
    cordova.exec(success, fail, "httpRequest", "SETPASSWORD", [account, $(inputs[1]).val()]);
});

function success(msg){
    alert(msg);
    window.location.href= "logintext.html";
}
function fail(msg){
    alert(msg);
}