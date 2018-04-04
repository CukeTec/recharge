$("#makeSureBtn").click(function(){
    var account = $("input[class='am-fl am-radius loginInput'").val();
    if(account.trim() == ""){
        alert("账号不可为空");
        return;
    }
    window.location.href = "forgetvalquestion.html?" + account;
});
