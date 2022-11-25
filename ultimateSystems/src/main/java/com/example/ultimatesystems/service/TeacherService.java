package com.example.ultimatesystems.service;

import com.example.ultimatesystems.entity.Student;
import com.example.ultimatesystems.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {

    List<Teacher> findAll();

    Optional<Teacher> findById(Long id);

    List<Teacher> findByName(String name, String surname);

    List<Teacher> findTeachersByStudentsId(Long teacherId);

    Teacher saveTeacher(Teacher teacher);

    void deleteById(Long id);
}
