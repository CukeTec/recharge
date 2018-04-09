var url = document.URL;
var account = url.split("?")[1];
function success1(msg){
    var questions = JSON.parse(msg);
    var selects = $("select[class='am-fl'");
    var options;
    $.each(questions, function(i,v){
        options = $(selects[i]).find("option");
        $.each(options, function(index,value){
            if($(value).val() == v.questionInnerId){
                $(value).attr("selected", "selected");
            }
        });
    $(selects[i]).attr("disabled", "disabled");
    })
}
function fail1(msg){

}
document.addEventListener('deviceready', function () {
     cordova.exec(success1, fail1, "httpRequest", "FORGETPASSWORD",[account]);

}, false);

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
//       question.questionName = $(selects[i]).find("option:selected").text();
       question.questionKey = $(v).val().trim();
       result.push(question);
    });
    cordova.exec(success, fail, "httpRequest", "VALIDATEQUESTION", [account, result]);
});

function success(msg){
    window.location.href = "findpwd.html?" + account;
}
function fail(msg){
    weakdialg(msg);
}