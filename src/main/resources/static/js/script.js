$(function(){
    console.log("the script is running");
    $("#btn").click(function (){
        $.get("login",$("#loginForm").serialize(),function(res){
            console.log("get click!!!");
            if(res.flag){//如果登录成功，跳转
                console.log("success!!!");
                redirect();
            }else{//否则显示错误信息
                $("#err_msg").html(res.message);
                console.log("fail");
            }
        },"json");
    });
})

var redirect = () => {
    let userAgent = navigator.userAgent.toLowerCase();
    let device = /ipad|iphone|midp|rv:1.2.3.4|ucweb|android|windows ce|windows mobile/;
    if(device.test(userAgent)) {
        // 跳转移动端页面
        window.location.href = 'phoneMain.html';
    } else {
        // 跳转PC端页面
        window.location.href = 'main.html';
    }
}