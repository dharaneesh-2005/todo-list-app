package com.todo.dao;
import com.todo.model.Todo;
import com.todo.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class TodoAppDAO {
    private Todo getTodoRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        boolean completed = rs.getBoolean("completed");
        java.sql.Timestamp created_at = rs.getTimestamp("created_at");
        java.sql.Timestamp updated_at = rs.getTimestamp("updated_at");
        return new Todo(id, title, description, completed, created_at.toLocalDateTime(), updated_at.toLocalDateTime());
    }
    public List<Todo> getALLTodos() throws SQLException {
        List<Todo> todos = new ArrayList<>();
        DatabaseConnection dbConn = new DatabaseConnection();
        try (
            Connection conn = dbConn.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos ORDER BY created_at DESC");
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                todos.add(getTodoRow(rs));
                
            }
        }
        return todos;
    }
}