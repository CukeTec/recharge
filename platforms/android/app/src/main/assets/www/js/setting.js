$(".makeSureBtn").click(function(){
    var selects = $("select[class='am-fl'");
    var inputs = $("input[class='am-fl'");
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
        alert("请选择问题");
        return;
    }
    if(dflag){
        alert("请选择不同的问题");
        return;
    }
    $.each(inputs, function(i,v){
        if($(v).val().trim() == "")  {
           nflag = true;
        }
    });
    if(nflag){
        alert("答案不可为空");
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
    cordova.exec(success, fail, "httpRequest", "SETQUESTION", [result]);
});

function success(msg){
    alert(msg);
}
function fail(msg){
    alert(msg);
}