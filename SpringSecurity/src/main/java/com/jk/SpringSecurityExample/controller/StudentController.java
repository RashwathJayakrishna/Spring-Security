package com.jk.SpringSecurityExample.controller;


import com.jk.SpringSecurityExample.module.Student;
import com.jk.SpringSecurityExample.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController<CsrfToken> {


    @Autowired
    private StudentService studentService;

    private List<Student> students=new ArrayList<>(List.of(
new Student(1,"AAA",500),
            new Student(2,"BBB",420)
    ));

    @GetMapping("/students")
    public List<Student> getStudent(){

        return students;
    }

    @GetMapping("/studentsByID")
    public Student getStudent(@RequestParam int id){

        return studentService.findById(id);

    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return  (CsrfToken) request.getAttribute("_csrf");
    }

   @PostMapping("/students")
    public Student addStudent(@RequestBody Student student){
students.add(student);
        return student;
    }
}
