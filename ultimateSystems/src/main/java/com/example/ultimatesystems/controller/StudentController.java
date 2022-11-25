package com.example.ultimatesystems.controller;

import com.example.ultimatesystems.entity.Student;
import com.example.ultimatesystems.entity.Teacher;
import com.example.ultimatesystems.service.StudentService;
import com.example.ultimatesystems.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {

        List<Student> students = new ArrayList<>(studentService.findAll());

        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/teachers/{teacherId}/students")
    public ResponseEntity<List<Student>> getAllStudentsByteacherId(@PathVariable(value = "teacherId") Long teacherId) {
        List<Student> Students = studentService.findStudentsByTeachersId(teacherId);

        return new ResponseEntity<>(Students, HttpStatus.OK);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentsById(@PathVariable(value = "id") Long id) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Not found Student with id = " + id));

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/students/{StudentId}/teachers")
    public ResponseEntity<List<Teacher>> getAllTeachersByStudentId(@PathVariable(value = "StudentId") Long StudentId) {
        List<Teacher> Teachers = teacherService.findTeachersByStudentsId(StudentId);

        return new ResponseEntity<>(Teachers, HttpStatus.OK);
    }

    @GetMapping("/students/{name}/{surname}")
    public ResponseEntity<List<Student>> getStudentByName(@PathVariable("name") String name, @PathVariable("name") String surname) {
        List<Student> students = new ArrayList<>(studentService.findByName(name, surname));

        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping("/teachers/{teacherId}/students")
    public ResponseEntity<Student> addStudent(@PathVariable(value = "teacherId") Long teacherId, @RequestBody @Valid Student studentRequest) {
        Student student = teacherService.findById(teacherId).map(teacher -> {
            long studentId = studentRequest.getId();

            if (studentId != 0L) {
                Student _student = studentService.findById(studentId)
                        .orElseThrow(() -> new ResourceAccessException("Not found Tag with id = " + studentId));
                teacher.addStudent(_student);
                teacherService.saveTeacher(teacher);
                return _student;
            }

            teacher.addStudent(studentRequest);
            return studentService.saveStudent(studentRequest);
        }).orElseThrow(() -> new ResourceAccessException("Not found Tutorial with id = " + teacherId));

        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createTeacher(@Valid @RequestBody Student student) {
        Student _student = studentService.saveStudent(new Student(student.getName(), student.getSurname(),
                student.getAge(), student.getEmail(),
                student.getMajor()));
        return new ResponseEntity<>(_student, HttpStatus.CREATED);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody @Valid Student StudentRequest) {
        Student Student = studentService.findById(id)
                .orElseThrow(() -> new ResourceAccessException("StudentId " + id + "not found"));

        Student.setName(StudentRequest.getName());
        Student.setSurname(StudentRequest.getSurname());
        Student.setAge(StudentRequest.getAge());
        Student.setEmail(StudentRequest.getEmail());
        Student.setMajor(StudentRequest.getMajor());

        return new ResponseEntity<>(studentService.saveStudent(Student), HttpStatus.OK);
    }

    @DeleteMapping("/teachers/{teacherId}/students/{StudentId}")
    public ResponseEntity<HttpStatus> deleteStudentFromTeacher(@PathVariable(value = "teacherId") Long teacherId, @PathVariable(value = "StudentId") Long StudentId) {
        Teacher teacher = teacherService.findById(teacherId)
                .orElseThrow(() -> new ResourceAccessException("Not found Teacher with id = " + teacherId));

        teacher.removeStudent(StudentId);
        teacherService.saveTeacher(teacher);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") long id) {
        studentService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
