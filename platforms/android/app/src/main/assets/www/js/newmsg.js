document.addEventListener('deviceready', function () {
     cordova.exec(success, fail, "httpRequest", "MESSAGEGROUP",[]);
}, false);

function success(msg){
    var data = JSON.parse(msg);
    $.each(data, function(i,v){
        var option = '<div class="am-fl"><label class="am-checkbox am-fl"><input type="checkbox" value="' + v.departmentId + '" data-am-ucheck/>' + v.departmentName + '</label></div>';
        $("div[class='am-modal-bd am-cf'").append(option);
    })
}

function fail(msg){
}

$(".makeSureBtn").click(function(){
    var name = $("input[class='am-fl inputbg'").val();
    var group = $("input[class='am-fl inputbg personinput'").val();
    var content = $("textarea[class='msgTextarea'").val();
    if(name.trim() == ""){
        weakdialg("请输入消息标题");
        return;
    }
    if(content.trim() == ""){
        weakdialg("请输入消息内容");
        return;
    }
    if(group == ""){
        weakdialg("请选择接收人");
        return;
    }
    cordova.exec(success1, fail1, "httpRequest", "SENDMESSAGE",[name, content, group]);
});

function success1(msg){
    weakdialg(msg);
}
function fail1(msg){
}

$('.personinput').off().on('focus',function(){
    $('#mymodal').modal({
        relatedTarget:this,
        onConfirm:function(options){
        var departmentIds = '';
        $.each($("input[type='checkbox']"),function(i,item){
            if($(item).is(':checked')){
                departmentIds+= $(item).val().trim()+",";
            }
        });
        departmentIds = departmentIds.substring(0,departmentIds.length-1);
        $('.personinput').val(departmentIds);
        }
    });
});