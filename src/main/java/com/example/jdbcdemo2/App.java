package com.example.jdbcdemo2;

import com.example.jdbcdemo2.model.Student;
import com.example.jdbcdemo2.repo.StudentRepo;

import java.sql.SQLException;
import java.util.Optional;

public class App {
    public static void main(String[] args) {
        // get connection
        // query
        // iterate result set and produce value
        //App-->Repo-->JDBC-->DB
        try (StudentRepo repo = new StudentRepo()) {
            repo.open();
           //insert(repo);
           // findById(repo,1);
            findAll(repo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void findAll(StudentRepo repo) throws SQLException {
        repo.findAll().forEach(System.out::println);
    }

    private static void findById(StudentRepo repo,Integer id) throws SQLException {
        Student student = repo.findById(id).orElse(null);
        System.out.println(student);

    }

    private static void insert(StudentRepo repo) throws SQLException {
        repo.save(new Student(1,"pramoth"));
        repo.save(new Student(2,"Non"));
        repo.save(new Student(3,"Ter"));
        repo.save(new Student(4,"op"));
        repo.save(new Student(5,"Ploy"));
    }

}
