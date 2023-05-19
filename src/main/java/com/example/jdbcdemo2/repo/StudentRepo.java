package com.example.jdbcdemo2.repo;

import com.example.jdbcdemo2.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepo implements AutoCloseable {
    private Connection connection;

    @Override
    public void close() throws Exception {
        System.out.println("closing connection.");
        if (connection != null) {
            connection.close();
            System.out.println("connection closed.");
        }
    }

    public void open() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:./target/db;AUTO_SERVER=TRUE", "sa", "sa");
    }

    public void save(Student st) throws SQLException {
        if (connection == null) {
            throw new IllegalStateException("must call open() first!");
        }
        try (Statement statement = connection.createStatement()) {
            // insert into student(id,name) values(1,'Pramoyh')
            String sql = String.format("insert into student(id,name) values(%d,'%s')", st.getId(), st.getName());
            System.out.println(sql);
            statement.executeUpdate(sql);
        }
    }
    public Optional<Student> findById(Integer id) throws SQLException {
        // select id,name from student where id=?
        try (PreparedStatement statement = connection.prepareStatement("select id,name from student where id=?")) {
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int id1 = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                Student student = new Student(id1,name);
                return Optional.of(student);
            }else{
                return Optional.empty();
            }
        }
    }

    public List<Student> findAll() throws SQLException {
        List<Student> results = new ArrayList<>();
        // select id,name from student where id=?
        try (PreparedStatement statement = connection.prepareStatement("select id,name from student")) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int id1 = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                Student student = new Student(id1,name);
                results.add(student);
            }
        }
        return results;
    }
}
