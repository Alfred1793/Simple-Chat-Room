package tech.cncloud.chat_room.controller;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.cncloud.chat_room.pojo.Result;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    @RequestMapping("/login")
    public Result login(String user, String pwd, HttpSession session){
        Result result=new Result();
        System.out.println(user);
        System.out.println(pwd);
        if(user!=null && "123".equals(pwd)){
            result.setFlag(true);
            session.setAttribute("user",user);
        }else{
            result.setFlag(false);
            result.setMessage("登录失败");
        }

        return result;
    }
    @RequestMapping("/getUsername")
    public String getUsername(HttpSession session){
        String username =(String)session.getAttribute("user");
        return username;
    }
}
