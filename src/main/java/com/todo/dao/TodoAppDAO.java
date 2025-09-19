package com.todo.dao;
import com.todo.model.Todo;
import com.todo.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class TodoAppDAO {
    private static final String SELCT_ALL_TODOS = "SELECT * FROM todos ORDER BY created_at DESC";
    private static final String INSERT_TODO = "INSERT INTO todos (title, description, completed,created_at,updated_at) VALUES (?, ?, ?,?,?)";

    public int createTodo(Todo todo) throws SQLException {
        DatabaseConnection dbConn = new DatabaseConnection();
        try (
            Connection conn = dbConn.getConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT_TODO, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, todo.getTitle());
            stmt.setString(2, todo.getDescription());
            stmt.setBoolean(3, todo.isCompleted());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(todo.getCreated_at()));
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(todo.getUpdated_at()));
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to create todo");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to get generated key");
                }
            }
        }
    }

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