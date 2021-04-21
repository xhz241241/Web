package org.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class loginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        //解析请求 header method url 请求数据
        String username = req.getParameter("username");//通过键获取值
        String password = req.getParameter("password");
        //获取输出流对象
        PrintWriter pw = resp.getWriter();
        //获取到了用户输入的名字密码
        //接下来就可以在数据库中根据用户输入的账号密码查询是否有该数据
        if("abc" .equals(username) && "abc".equals(password)){
            pw.println("<h1>欢迎你 : " + username + "</h1>");
        }else{
            pw.println("<h2>您输入的用户名或密码有误, 请检查后再输入~ </h2>");
        }
    }
}
