package com.example.ultimatesystems.service;

import com.example.ultimatesystems.entity.Student;
import com.example.ultimatesystems.entity.Teacher;
import com.example.ultimatesystems.repository.StudentRepository;
import com.example.ultimatesystems.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        return teacherRepository.findById(id);
    }

    @Override
    public List<Teacher> findByName(String name, String surname) {
        return teacherRepository.findAll()
                .stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .filter(e -> e.getSurname().equalsIgnoreCase(surname))
                .collect(Collectors.toList());
    }

    @Override
    public List<Teacher> findTeachersByStudentsId(Long teacherId) {
        return teacherRepository.findTeachersByStudentsId(teacherId);
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }
}
