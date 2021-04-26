package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.ArticleDAO;
import org.example.dao.UserDAO;
import org.example.model.Article;
import org.example.model.JSONResponse;
import org.example.model.User;
import org.example.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        //业务: 文章列表查询(此处查询了所有文章)
        //通过数据库查询当前用户文章的数据
        JSONResponse jsonResponse = new JSONResponse();
        try {
            User user = JSONUtil.deserialize(req.getInputStream(), User.class);
            // 根据输入的账号密码查询是否有数据
            User query = UserDAO.query(user);
            if(query == null){
                //业务失败
                jsonResponse.setCode("LOG001");
                jsonResponse.setMessage("用户名或密码错误");
            }else{
                //业务处理成功
                jsonResponse.setSuccess(true);
                HttpSession session = req.getSession();
                session.setAttribute("user", query);
            }
        }catch (Exception e) {
            //业务处理异常
            //打印异常信息
            e.printStackTrace();
            //返回false+错误码+错误信息
            //虽然默认是失败但最好写上
            jsonResponse.setCode("ERR");
            jsonResponse.setMessage("系统出错");
        }

        String s = JSONUtil.serialize(jsonResponse);
        resp.getWriter().println(s);
    }
}
