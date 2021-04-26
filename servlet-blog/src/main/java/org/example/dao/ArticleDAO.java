package org.example.dao;

import org.example.model.Article;
import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {
    public static List<Article> query(int userId) throws SQLException {

        // 1. 和服务器进行连接(connection对象)
        Connection connection = DBUtil.getConnection();
        System.out.println(connection);

        // 2. 创建操作命令对象(statement)
        String s = "select id, title from article where user_id = ?";
            //预编译
        PreparedStatement statement = connection.prepareStatement(s);
            //替换占位符
        statement.setInt(1, userId);

        // 3. 创建并执行sql语句
        ResultSet res = statement.executeQuery();

        List<Article> list = new ArrayList<>();
        // 4. 查询操作需要返回结果集
        while(res.next()){
            int id = res.getInt("id");
            String title = res.getString("title");
            Article a = new Article();
            a.setId(id);
            a.setTitle(title);
            list.add(a);
        }

        // 5. 清理资源
        DBUtil.allClose(connection, statement, res);

        return list;
    }


    public static void main(String[] args) throws SQLException {
        System.out.println(query(1));
//        System.out.println(query(1).get(1));
//        System.out.println(query(1).get(2));
    }

    //向数据库中插入一条新记录
    public static int insert(Article a, Integer user_id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 1. 连接服务器
            connection = DBUtil.getConnection();
            // 2. 创建 sql 语句
            String sql = "insert into article(title, content, user_id) values(?, ?, ?)";
            // 3. 创建操作命令对象
            preparedStatement = connection.prepareStatement(sql);
            // 4. 替换占位符 执行 sql
            preparedStatement.setString(1, a.getTitle());
            preparedStatement.setString(2, a.getContent());
            preparedStatement.setInt(3, user_id);
                //执行,插入,修改删除操作 调用的都是executeUpdate方法, 返回值都是 int
            return preparedStatement.executeUpdate();
        } finally {
            DBUtil.allClose(connection, preparedStatement);
        }
    }

    //通过文章id查询该文章
    public static Article queryById(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DBUtil.getConnection();
            String s = "select * from article where id = ?";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            Article a = null;
            while(resultSet.next()){
                a = new Article();
                a.setId(id);
                a.setTitle(resultSet.getString("title"));
                a.setContent(resultSet.getString("content"));
            }
            return a;
        }finally {
            DBUtil.allClose(connection, preparedStatement, resultSet);
        }
    }

    //修改文章内容
    public static int update(Article a) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 1. 连接服务器
            connection = DBUtil.getConnection();
            // 2. 创建 sql 语句
            String sql = "update article set title = ?, content = ? where id = ?";
            // 3. 创建操作命令对象
            preparedStatement = connection.prepareStatement(sql);
            // 4. 替换占位符 执行 sql
            preparedStatement.setString(1, a.getTitle());
            preparedStatement.setString(2, a.getContent());
            preparedStatement.setInt(3, a.getId());
            //执行,插入,修改删除操作 调用的都是executeUpdate方法, 返回值都是 int
            return preparedStatement.executeUpdate();
        } finally {
            DBUtil.allClose(connection, preparedStatement);
        }
    }

    public static int delete(String[] s_id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 1. 连接服务器
            connection = DBUtil.getConnection();
            // 2. 创建 sql 语句
            StringBuilder sql = new StringBuilder("delete from article where id in(");
            for (int i = 0; i < s_id.length; i++) {
                //期望结果是"?, ?, ?"
                //所以第一个前不用加 ?
                if(i != 0){
                    sql.append(",");
                }
                sql.append("?");
            }
            sql.append(")");
            // 3. 创建操作命令对象
            preparedStatement = connection.prepareStatement(sql.toString());
            // 4. 替换占位符 执行 sql
                //由于占位符替换的索引是从 1 开始的 所以需要 i+1
            for (int i = 0; i < s_id.length; i++) {
                preparedStatement.setInt(i+1, Integer.parseInt(s_id[i]));
            }
            //执行,插入,修改删除操作 调用的都是executeUpdate方法, 返回值都是 int
            return preparedStatement.executeUpdate();
        } finally {
            DBUtil.allClose(connection, preparedStatement);
        }
    }
}
