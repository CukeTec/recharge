document.addEventListener('deviceready', function () {
     cordova.exec(success, fail, "httpRequest", "MESSAGEGROUP",[]);
}, false);

function success(msg){
    var data = JSON.parse(msg);
    $.each(data, function(i,v){
        var option = '<option value="' + v.departmentId + '">' + v.departmentName + '</option>';
        $("select[class='am-fl'").append(option);
    })
}

function fail(msg){
}

$(".makeSureBtn").click(function(){
    var name = $("input[class='am-fl inputbg'").val();
    var group = $("select[class='am-fl'").find("option:selected").val();
    var content = $("textarea[class='msgTextarea'").val();
    if(name.trim() == ""){
        alert("请输入消息标题");
        return;
    }
    if(content.trim() == ""){
        alert("请输入消息内容");
        return;
    }
    if(group == 0){
        alert("请选择接收人");
        return;
    }
    cordova.exec(success1, fail1, "httpRequest", "SENDMESSAGE",[name, content, group]);
});

function success1(msg){
    alert(msg);
}
function fail1(msg){
}

