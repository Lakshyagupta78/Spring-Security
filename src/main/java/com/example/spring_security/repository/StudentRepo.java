package com.example.spring_security.repository;

import com.example.spring_security.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Long> {
}
