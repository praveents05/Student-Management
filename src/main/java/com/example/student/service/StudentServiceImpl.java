package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository repo;

   @Override
public Page<Student> getStudents(int page, String keyword) {

    Pageable pageable = PageRequest.of(page, 5, Sort.by("id").ascending());

    if (keyword == null || keyword.trim().isEmpty()) {
        return repo.findAll(pageable);
    }

    return repo.findByNameContainingIgnoreCase(keyword, pageable);
}

    @Override
    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public void deleteStudent(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return repo.findAll();
    }
}