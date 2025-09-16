package com.todo;
import com.todo.model.Todo;
import com.todo.util.DatabaseConnection;
public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConn = new DatabaseConnection();
        try {
            dbConn.getConnection();
            System.out.println("Database connected successfully.");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}