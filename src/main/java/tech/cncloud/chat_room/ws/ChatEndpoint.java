package tech.cncloud.chat_room.ws;

import org.springframework.stereotype.Component;
import tech.cncloud.chat_room.utils.MessageUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndpoint {

    //用来存储每一个客户端对象对应的ChatEndpoint对象
    private static Map<String,ChatEndpoint> onlineUsers =new ConcurrentHashMap<>();

    //声明session对象，通过改对象可以发送消息给指定的用户
    private Session session;

    private HttpSession httpSession;

    @OnOpen
    //链接建立时执行
    public void onOpen(Session session, EndpointConfig config){
        this.session=session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession=httpSession;

        String username = (String) httpSession.getAttribute("user");
        onlineUsers.put(username,this);

        String message = MessageUtils.getMessage(true,null,getNames());
    }

    private void broadcastAllUsers(String message){
        try {
            Set<String>names=onlineUsers.keySet();
            for(String name:names){
                ChatEndpoint chatEndpoint = onlineUsers.get(name);
                chatEndpoint.session.getBasicRemote().sendText(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private Set<String> getNames(){
        return onlineUsers.keySet();
    }
    @OnMessage
    //接收到客户端发送的数据时被调用
    public void onMessage(String message,Session session){

    }

    @OnClose
    //链接关闭时被调用
    public void onClose(Session sessio){

    }
}