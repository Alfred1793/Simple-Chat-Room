$(function(){
    console.log("the script is running");
    $("#btn").click(function (){
        $.get("login",$("#loginForm").serialize(),function(res){
            console.log("get click!!!");
            if(res.flag){//如果登录成功，跳转
                console.log("success!!!");
                location.href="main.html";
            }else{//否则显示错误信息
                $("#err_msg").html(res.message);
                console.log("fail");
            }
        },"json");
    });
})