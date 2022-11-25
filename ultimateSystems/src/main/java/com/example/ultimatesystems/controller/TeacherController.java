package com.example.ultimatesystems.controller;

import com.example.ultimatesystems.entity.Student;
import com.example.ultimatesystems.entity.Teacher;
import com.example.ultimatesystems.service.StudentService;
import com.example.ultimatesystems.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/teachers")
    public ResponseEntity<List<Teacher>> getAllteachers() {

        List<Teacher> teachers = new ArrayList<>(teacherService.findAll());

        if (teachers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") long id) {
        Teacher teacher = teacherService.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Not found teacher with id = " + id));

        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @GetMapping("/teachers/{name}/{surname}")
    public ResponseEntity<List<Teacher>> getTeacherByName(@PathVariable("name") String name, @PathVariable("name") String surname) {
        List<Teacher> teachers = new ArrayList<>(teacherService.findByName(name, surname));

        if (teachers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @PostMapping("/teachers")
    public ResponseEntity<Teacher> createTeacher(@RequestBody @Valid Teacher teacher) {
        Teacher _teacher = teacherService.saveTeacher(new Teacher(teacher.getName(), teacher.getSurname(),
                teacher.getAge(), teacher.getEmail(),
                teacher.getSubject()));
        return new ResponseEntity<>(_teacher, HttpStatus.CREATED);
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") long id, @RequestBody @Valid Teacher teacher) {
        Teacher _teacher = teacherService.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Not found teacher with id = " + id));

        _teacher.setName(teacher.getName());
        _teacher.setSurname(teacher.getSurname());
        _teacher.setAge(teacher.getAge());
        _teacher.setEmail(teacher.getEmail());
        _teacher.setSubject(teacher.getSubject());

        return new ResponseEntity<>(teacherService.saveTeacher(_teacher), HttpStatus.OK);
    }

    @DeleteMapping("/students/{studentId}/teachers/{teacherId}")
    public ResponseEntity<HttpStatus> deleteTeacherFromStudent(@PathVariable(value = "studentId") Long studentId, @PathVariable(value = "teacherId") Long teacherId) {
        Student student = studentService.findById(studentId)
                .orElseThrow(() -> new ResourceAccessException("Not found Teacher with id = " + studentId));

        student.removeTeacher(studentId);
        studentService.saveStudent(student);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/students/{studentId}/teachers")
    public ResponseEntity<Teacher> addTeacher(@PathVariable(value = "studentId") Long studentId, @RequestBody @Valid Teacher teacherRequest) {
        Teacher teacher = studentService.findById(studentId).map(student -> {
            long teacherId = teacherRequest.getId();

            if (teacherId != 0L) {
                Teacher _teacher = teacherService.findById(teacherId)
                        .orElseThrow(() -> new ResourceAccessException("Not found Tag with id = " + teacherId));
                student.addTeacher(_teacher);
                studentService.saveStudent(student);
                return _teacher;
            }

            student.addTeacher(teacherRequest);
            return teacherService.saveTeacher(teacherRequest);
        }).orElseThrow(() -> new ResourceAccessException("Not found Tutorial with id = " + studentId));

        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<HttpStatus> deleteTeacher(@PathVariable("id") long id) {
        teacherService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
