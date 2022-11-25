package com.example.ultimatesystems.service;

import com.example.ultimatesystems.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAll();

    Optional<Student> findById(Long id);

    List<Student> findByName(String name, String surname);

    List<Student> findStudentsByTeachersId(Long studentId);

    Student saveStudent(Student student);

    void deleteById(Long id);
}
