$(".makeSureBtn").click(function(){
    var inputs = $("input[class='am-fl'");
    console.log(inputs[1].value + "\t" + inputs[2].value)
    if(inputs[1].value != inputs[2].value){
        alert("密码不一致");
    }
    cordova.exec(success, fail, "httpRequest", "changepwd", ["http://sireyun.com:8081/PSMGABService/changeUserPW", inputs[0].value, inputs[1].value]);
});

function success(msg){
    alert(msg);
}
function fail(msg){
    alert(msg);
}