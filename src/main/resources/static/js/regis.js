$(function(){
    console.log("the script is running");
    $("#btn2").click(function (){
        $.get("regis",$("#loginForm").serialize(),function(res){
            console.log("get click!!!");
            window.location.href = 'login.html';
        },"json");
    });
})