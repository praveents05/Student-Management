package com.example.student.service;

import com.example.student.entity.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    Page<Student> getStudents(int page, String keyword);

    Student saveStudent(Student student);

    Student getStudentById(Long id);

    void deleteStudent(Long id);

    List<Student> getAllStudents();
}