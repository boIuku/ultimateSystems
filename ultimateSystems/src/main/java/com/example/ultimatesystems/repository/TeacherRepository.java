package com.example.ultimatesystems.repository;

import com.example.ultimatesystems.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    List<Teacher> findAll();
    List<Teacher> findTeachersByStudentsId(Long teacherId);
}
