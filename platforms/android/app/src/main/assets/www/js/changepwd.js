$(".makeSureBtn").click(function(){
    var inputs = $("input[class='am-fl inputbg'");
    if(inputs[1].value != inputs[2].value){
        weakdialg("密码不一致");
        return;
    }
    if(inputs[1].value.trim() == ""){
        weakdialg("密码不可为空");
        return;
    }
    cordova.exec(success, fail, "httpRequest", "CHANGEPASSWORD", [inputs[0].value, inputs[1].value]);
});

function success(msg){
    weakdialg(msg);
    window.location.href="app.html";
}
function fail(msg){
        var data = JSON.parse(msg);
        weakdialg(data.msg);
        if(data.code == "405"){
            window.location.href = "logintext.html";
        }
}