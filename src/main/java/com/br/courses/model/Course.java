package com.br.courses.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "course_load", nullable = false)
    private Long courseLoad;
    @Column(name = "completion_date", nullable = false)
    private Date completionDate;
    //Implementation after
    //private List<String> disciplines;
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Student> students;

    public Course(String name, Long courseLoad, Date completionDate) {
        this.name = name;
        this.courseLoad = courseLoad;
        this.completionDate = completionDate;
        this.students = new ArrayList<>();
    }

    public Course(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCourseLoad() {
        return courseLoad;
    }

    public void setCourseLoad(Long courseLoad) {
        this.courseLoad = courseLoad;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
