package com.example.student;

import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.service.StudentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentApplicationTests {

    @Autowired
    private StudentService service;

    @Autowired
    private StudentRepository repo;

    @Test
    void contextLoads() {
        assertNotNull(service);
    }

    @Test
    void deleteRandomEntryShouldRemoveOnlyThatRecord() {

        // clear table
        repo.deleteAll();

        // ---------- create students ----------
        Student s1 = new Student();
        s1.setName("A");
        s1.setDepartment("X");
        s1.setGender("M");
        s1.setMarks(10);

        Student s2 = new Student();
        s2.setName("B");
        s2.setDepartment("Y");
        s2.setGender("F");
        s2.setMarks(20);

        Student s3 = new Student();
        s3.setName("C");
        s3.setDepartment("Z");
        s3.setGender("M");
        s3.setMarks(30);

        // ---------- save ----------
        s1 = service.saveStudent(s1);
        s2 = service.saveStudent(s2);
        s3 = service.saveStudent(s3);

        // ---------- delete ----------
        Long idToDelete = s2.getId();
        service.deleteStudent(idToDelete);

        // ---------- fetch remaining ----------
        List<Student> remaining = service.getAllStudents();

        // should be 2 left
        assertEquals(2, remaining.size());

        // deleted id should not exist
        boolean exists = remaining.stream()
                .anyMatch(s -> s.getId().equals(idToDelete));

        assertFalse(exists);
    }
}