package com.jk.SpringSecurityExample.service;

import com.jk.SpringSecurityExample.module.Student;
import com.jk.SpringSecurityExample.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public Student findById(int id) {

        return studentRepo.findById(id).orElse(null);

    }
}
