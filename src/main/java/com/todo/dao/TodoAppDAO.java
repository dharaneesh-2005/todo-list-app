package com.todo.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import javax.xml.crypto.Data;
import com.model.Todo;
import com.todo.util.DatabaseConnection;

public class TodoAppDAO {

    private static final String SELECT_ALL_TODOS = "select * from todos";// ORDER BY created_at DESC";
    private static final String INSERT_TODO = "INSERT INTO todo (title, description, completed, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_TODO_BY_ID = "select * from todos where id = ?";
    //Create a New Todo
    public int createtodo(Todo todo) throws SQLException {
        try (
                Connection conn = DatabaseConnection.getDBConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_TODO, Statement.RETURN_GENERATED_KEYS);
        ) {
            System.out.println("Connecting to database...");
            stmt.setString(1, todo.getTitle());
            stmt.setString(2, todo.getDescription());
            stmt.setBoolean(3, todo.isCompleted());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(todo.getCreated_at()));
            stmt.setObject(5, todo.getUpdated_at());

            int rowAffected = stmt.executeUpdate();
            if(rowAffected == 0){
                throw new SQLException("Creating todo failed, no rows affected.");
            }
        }
        return 0;
    }


    private Todo getTodoRow(ResultSet res) throws SQLException{
        return new Todo(
                res.getInt("id"),
                res.getString("title"),
                res.getString("description"),
                res.getBoolean("completed"),
                res.getTimestamp("created_at").toLocalDateTime(),
                res.getTimestamp("updated_at").toLocalDateTime()
        );
    }
    public List<Todo> getAllTodos() throws SQLException {
        List<Todo> todos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos ORDER BY created_at DESC");
             ResultSet res = stmt.executeQuery();
        ) {
            System.out.println("Query executed successfully!");
            while (res.next()) {
                Todo todo = getTodoRow(res);
                System.out.println(todo); // Print each Todo object for debugging
                todos.add(todo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }
    public static String getSelectAllTodos() {
        return SELECT_ALL_TODOS;
    }

    public static String getInsertTodo() {
        return INSERT_TODO;
    }
    public Todo getTodoById(int id) throws SQLException {
        try (
                Connection conn = DatabaseConnection.getDBConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos where id = ?");
        )
        {
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            if(res.next()){
                getTodoRow(res);
            }
        }
        return null;
    }

}