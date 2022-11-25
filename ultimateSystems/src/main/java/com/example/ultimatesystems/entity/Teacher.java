package com.example.ultimatesystems.entity;

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
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 200, message
            = "Name should be longer than 2 characters")
    private String name;

    @Size(min = 2, max = 200, message
            = "Surname should be longer than 2 characters")
    private String surname;

    @Min(value = 18, message = "Age should not be less than 18")
    private int age;

    @Email(message = "Email should be valid")
    private String email;

    private String subject;

    @ManyToMany(mappedBy = "teachers",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    private Set<Student> students;

    public Teacher(String name, String surname, int age, String email, String subject) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.subject = subject;
    }

    public void addStudent(Student student) {
        this.students.add(student);
        student.getTeachers().add(this);
    }

    public void removeStudent(long studentId) {
        Student student = this.students.stream().filter(t -> t.getId() == studentId).findFirst().orElse(null);
        if (student != null) {
            this.students.remove(student);
            student.getTeachers().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Teacher [id=" + id + ", name=" + name + ", surname=" + surname + ", age=" + age  + ", email=" + email  + ", subject=" + subject +"]";
    }

}
