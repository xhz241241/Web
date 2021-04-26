package org.example.dao;

import org.example.model.User;
import org.example.util.DBUtil;

import java.sql.*;
import java.util.Date;

public class UserDAO {

    public static User query(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection  = DBUtil.getConnection();
            String s = "select id, nickname, sex, birthday, head " +
                    "from user where username = ? and password = ? ";
            preparedStatement = connection.prepareStatement(s);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();
            User query = null;
            while(resultSet.next()){
                query = new User();
                query.setId(resultSet.getInt("id"));
                query.setUsername(user.getUsername());
                query.setPassword(user.getPassword());
                query.setNickname(resultSet.getString("nickname"));
                query.setSex(resultSet.getBoolean("sex"));

                Timestamp t = resultSet.getTimestamp("birthday");
                if(t != null){
                    query.setBirthday(new Date(t.getTime()));
                }
                query.setHead(resultSet.getString("head"));
            }
            return query;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            DBUtil.allClose(connection, preparedStatement, resultSet);
        }
    }
}
