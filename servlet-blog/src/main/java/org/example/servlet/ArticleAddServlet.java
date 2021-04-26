package org.example.servlet;

import org.example.dao.ArticleDAO;
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

@WebServlet("/articleAdd")
public class ArticleAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        JSONResponse jsonResponse = new JSONResponse();
        //业务代码随时可能出现异常 所以全部写在 try 里
        try{
            //解析数据
                // 1. 把json对象反序列化为java对象
            Article a = JSONUtil.deserialize(req.getInputStream(), Article.class);
            HttpSession session = req.getSession(false);
            User user = (User)session.getAttribute("user");
            //处理业务
                // 1. 在数据库中插入一条新记录
            int tem = ArticleDAO.insert(a, user.getId());
            jsonResponse.setSuccess(true);
            //业务处理成功 设置业务数据

        }catch (Exception e){
            //业务异常
            //首先把异常的堆栈信息打印出来 不要吃异常
            e.printStackTrace();
            //展示给用户的错误信息
            jsonResponse.setCode("ERR");
            jsonResponse.setMessage("后端程序出错, 请联系管理员");

        }
        //序列化返回响应数据
        String s = JSONUtil.serialize(jsonResponse);
        resp.getWriter().println(s);
    }
}
