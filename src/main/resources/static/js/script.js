$(function(){
    $("#bin").click(function (){
        $.get("login",$("#loginForm").serialize(),function(res){
            if(res.flag){//如果登录成功，跳转
                location.href="main.html";
            }else{//否则显示错误信息
                $("#err_msg").html(res.message());
            }
        },"json");
    });
})