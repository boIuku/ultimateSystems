package com.example.ultimatesystems.service;

import com.example.ultimatesystems.entity.Student;
import com.example.ultimatesystems.repository.StudentRepository;
import com.example.ultimatesystems.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> findByName(String name, String surname) {
        return studentRepository.findAll()
                .stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .filter(e -> e.getSurname().equalsIgnoreCase(surname))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByTeachersId(Long studentId) {
        return studentRepository.findStudentsByTeachersId(studentId);
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}
