package com.example.spring_security.controller;

import com.example.spring_security.entity.Student;
import com.example.spring_security.repository.StudentRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentRepo sr;

    @PostMapping("/addStudent")
    public Student addStudent(@RequestBody Student student){
        sr.save(student);
        return student;
    }

    @GetMapping("/allStudent")
    public List<Student> allStudent(){
       return sr.findAll();
    }

    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }

}
