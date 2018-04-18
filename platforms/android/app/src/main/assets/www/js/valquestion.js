var url = document.URL;
var param = url.split("?")[1];
var id = param.split("&");
var selects = $("select[class='am-fl'");
var options;
$.each(id, function(i,v){
    options = $(selects[i]).find("option");
    $.each(options, function(index,value){
        if($(value).val() == v){
            $(value).attr("selected", "selected");
        }
    });
    $(selects[i]).attr("disabled", "disabled");
})

$(".makeSureBtn").click(function(){
    var selects = $("select[class='am-fl'");
    var inputs = $("input[class='am-fl settingInput'");
    var selectArray = [];
    var dflag = false;
    var sflag = false;
    var nflag = false;
    $.each(selects, function(i,v){
        var id = $(v).find("option:selected").val();
        if(id == 0){
            sflag = true;
        }
        if($.inArray(id, selectArray) == -1){
            selectArray.push(id);
        }else{
            dflag = true;
        }
    });
    if(sflag){
        weakdialg("请选择问题");
        return;
    }
    if(dflag){
        weakdialg("请选择不同的问题");
        return;
    }
    $.each(inputs, function(i,v){
        if($(v).val().trim() == "")  {
           nflag = true;
        }
    });
    if(nflag){
        weakdialg("答案不可为空");
        return;
    }
    var result = [];
    var question;
    $.each(inputs, function(i,v){
       question = {};
       question.questionInnerId = selectArray[i];
       question.questionName = $(selects[i]).find("option:selected").text();
       question.questionKey = $(v).val().trim();
       result.push(question);
    });
    cordova.exec(success, fail, "httpRequest", "VALIDATEQUESTION", [result]);
});

function success(msg){
      window.location.href = "setting.html";
}
function fail(msg){
    var data = JSON.parse(msg);
            weakdialg(data.msg);
            if(data.code == "405"){
                window.location.href = "logintext.html";
            }
}