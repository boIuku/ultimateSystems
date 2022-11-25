package com.example.ultimatesystems.repository;

import com.example.ultimatesystems.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAll();

    List<Student> findStudentsByTeachersId(Long tutorialId);
}
