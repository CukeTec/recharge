var account;
$("#makeSureBtn").click(function(){
    account = $("input[class='am-fl am-radius loginInput'").val();
    if(account.trim() == ""){
        weakdialg("账号不可为空");
        return;
    }
    cordova.exec(success1, fail1, "httpRequest", "FORGETPASSWORD",[account]);
});
function success1(msg){
    var data = JSON.parse(msg);
    if(data.length == 3){
        window.location.href = "forgetvalquestion.html?" + account;
    }else{
        weakdialg("账号不存在或未设置密保问题，请联系管理员");
    }
}
function fail1(msg){
        var data = JSON.parse(msg);
        weakdialg(data.msg);
        if(data.code == "405"){
            window.location.href = "logintext.html";
        }
}