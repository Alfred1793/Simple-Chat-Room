var toName;
var username;
//点击好友名称展示相关消息
function showChat(name){
    toName = name;
    //现在聊天框
    $("#content").html("");
    $("#content").css("visibility","visible");
    $("#Inchat").html("<p style=\"text-align: center;font-weight: 400;\">与用户"+toName+"聊天</p>");
    //从sessionStorage中获取历史信息
    var chatData = sessionStorage.getItem(toName);
    if (chatData != null){
        $("#content").html(chatData);
    }
}
$(function () {
    $.ajax({
        url:"getUsername",
        success:function (res) {
            username = res;
        },
        async:false //同步请求，只有上面好了才会接着下面
    });
    //建立websocket连接
    //获取host解决后端获取httpsession的空指针异常
    var host = window.location.host;
    var ws = new WebSocket("ws://"+host+"/chat");
    ws.onopen = function (evt) {
        $("#username").html("<h3 style=\"text-align: center;\">用户："+ username +"<span>-在线</span></h3>");
    }
    //接受消息
    ws.onmessage = function (evt) {
        console.log("onmessage");
        //获取服务端推送的消息
        var dataStr = evt.data;
        //将dataStr转换为json对象
        var res = JSON.parse(dataStr);

        //判断是否是系统消息
        if(res.system){
            console.log("isSystem");
            //系统消息
            //1.好友列表展示
            //2.系统广播的展示
            //此处声明的变量是调试时命名的，可以直接合并
            var names = res.message;
            var userlistStr = "";
            var broadcastListStr = "";
            for (var name of names){
                console.log(name);
                if (name != username){
                    temp = "<li className=\"rel-item\"><a onClick='showChat(\""+name+"\")'>"+name+"</a></li>"
                    userlistStr = userlistStr + temp;
                    broadcastListStr += "<p style='text-align: center'>"+ name +"上线了</p>";
                }
            }
            //渲染好友列表和系统广播
            $("#user-list").html(userlistStr);
            $("#broadcast").html(broadcastListStr);

        }else {
            console.log("isnotSystem");
            //不是系统消息
            var str = "<span id='mes_left'>"+ res.message +"</span></br>";
            if (toName === res.fromName) {
                $("#content").append(str);
            }
            var chatData = sessionStorage.getItem(res.fromName);
            if (chatData != null){
                str = chatData + str;
            }
            //保存聊天消息
            sessionStorage.setItem(res.fromName,str);
        };
    }
    ws.onclose = function () {
        $("#username").html("<h3 style=\"text-align: center;\">用户："+ username +"<span>-离线</span></h3>");
    }

    //发送消息
    $("#submit").click(function () {
        //1.获取输入的内容
        var data = $("#input_text").val();
        //2.清空发送框
        $("#input_text").val("");
        var json = {"toName": toName ,"message": data};
        //将数据展示在聊天区
        var str = "<span id='mes_right'>"+ data +"</span></br>";
        $("#content").append(str);

        var chatData = sessionStorage.getItem(toName);
        if (chatData != null){
            str = chatData + str;
        }
        sessionStorage.setItem(toName,str);
        //3.发送数据
        ws.send(JSON.stringify(json));
    })
})