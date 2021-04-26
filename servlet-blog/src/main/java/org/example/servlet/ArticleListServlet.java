package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.ArticleDAO;
import org.example.model.Article;
import org.example.model.JSONResponse;
import org.example.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/articleList")
public class ArticleListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        //业务: 文章列表查询(此处查询了所有文章)
        //通过数据库查询当前用户文章的数据
        JSONResponse jsonResponse = new JSONResponse();
        try {
            // 查询用户id为1的用户的文章
            HttpSession session = req.getSession(false);
            User user = (User)session.getAttribute("user");
            List<Article> query = ArticleDAO.query(user.getId());
            //业务处理成功
            //返回true+业务数据
            jsonResponse.setSuccess(true);
            jsonResponse.setData(query);
        }catch (Exception e) {
            //业务处理异常
            //打印异常信息
            e.printStackTrace();
            //返回false+错误码+错误信息
            //虽然默认是失败但最好写上
            jsonResponse.setCode("ERR");
            jsonResponse.setMessage("系统出错");
        }
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(jsonResponse);
        resp.getWriter().println(s);
    }
}
