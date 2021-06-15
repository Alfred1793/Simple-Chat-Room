package tech.cncloud.chat_room.controller;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.cncloud.chat_room.pojo.Result;
import java.sql.*;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://rm-bp1t3q5a46n1fhf961250101m.mysql.rds.aliyuncs.com:3306/chat_room";
    static final String USER = "software";
    static final String PASS = "nbu-18CS";

    @RequestMapping("/login")
    public Result login(String user, String pwd, HttpSession session){
        Result result=new Result();
        System.out.println(user);
        System.out.println(pwd);

        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "select * from user where username='"+user+"' && passward='"+pwd+"'";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            if(rs.next()){
                System.out.println("成功登录");
                result.setFlag(true);
                session.setAttribute("user",user);
            }else{
                System.out.println("登录失败");
                result.setFlag(false);
                result.setMessage("登录失败");
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");

        return result;
    }
    @RequestMapping("/getUsername")
    public String getUsername(HttpSession session){
        String username =(String)session.getAttribute("user");
        return username;
    }
}
