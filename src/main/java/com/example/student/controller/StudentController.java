package com.example.student.controller;

import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {
private final StudentRepository repo;   // Declare repo

    public StudentController(StudentRepository repo) {
        this.repo = repo;
    }
    @Autowired
    private StudentService service;

    @GetMapping("/students")
    public String viewStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            Model model) {

        Page<Student> studentPage = service.getStudents(page, keyword);

        
        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "list";
    }

    @GetMapping("/add")
public String addForm(Model model) {
    model.addAttribute("student", new Student());
    return "student";  // must match filename
}

   @PostMapping("/students/save")
public String saveStudent(@ModelAttribute Student student) {
    repo.save(student);   // If ID exists → Update
    return "redirect:/students";
}

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
public String showEditForm(@PathVariable Long id, Model model) {

    Student student = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));

    model.addAttribute("student", student);

    return "edit-student";
}

@PostMapping("/update/{id}")
public String updateStudent(@PathVariable Long id,
                            @ModelAttribute Student student) {

    Student existing = service.getStudentById(id);

    existing.setName(student.getName());
    existing.setDepartment(student.getDepartment());
    existing.setGender(student.getGender());
    existing.setMarks(student.getMarks());

    service.saveStudent(existing);

    return "redirect:/students";
}
}