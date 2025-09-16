package com.todo.util;
import java.sql.Connection;
import java.sql.DriverManager;// for jdbc connection
import java.sql.SQLException;
public class DatabaseConnection {
    public static final String URL = "jdbc:mysql://localhost:3306/todo";
    public static final String USERNAME="root";
    public static final String PASSWORD="1234";

    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
        }
    }
    public  Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}