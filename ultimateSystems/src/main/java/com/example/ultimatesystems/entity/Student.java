package com.example.ultimatesystems.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 200, message
            = "Name should be longer than 2 characters")
    private String name;

    @Size(min = 2, max = 200, message
            = "Surame should be longer than 2 characters")
    private String surname;

    @Min(value = 18, message = "Age should not be less than 18")
    private int age;

    @Email(message = "Email should be valid")
    private String email;

    private String major;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "student_teacher_relation",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "teacher_id") }
    )
    @JsonIgnore
    private Set<Teacher> teachers;

    public Student(String name, String surname, int age, String email, String major) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.major = major;
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        teacher.getStudents().add(this);
    }

    public void removeTeacher(long teacherId) {
        Teacher teacher = this.teachers.stream().filter(t -> t.getId() == teacherId).findFirst().orElse(null);
        if (teacher != null) {
            this.teachers.remove(teacher);
            teacher.getStudents().remove(this);
        }
    }
}
