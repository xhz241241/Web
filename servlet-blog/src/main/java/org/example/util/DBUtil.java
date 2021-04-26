package org.example.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class DBUtil {
    private  static  MysqlDataSource dataSource = new MysqlDataSource();
    static{
        dataSource.setUser("root");
        dataSource.setPassword("000000");
        dataSource.setURL("jdbc:mysql://localhost:3306/servlet_blog?characterEncoding=utf8&useSSL=true");
        dataSource.setUseSSL(false);
    }
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void allClose(Connection connection, PreparedStatement statement, ResultSet res) throws SQLException {
        if (res != null) {
            res.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public static void allClose(Connection connection, PreparedStatement preparedStatement) throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
