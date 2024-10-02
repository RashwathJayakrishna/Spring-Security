package com.jk.SpringSecurityExample.repo;

import com.jk.SpringSecurityExample.module.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
}
