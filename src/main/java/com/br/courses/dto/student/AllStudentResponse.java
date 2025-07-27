package com.br.courses.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AllStudentResponse {

    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private RegisteredCourseDto course;
    private Boolean active;

    public AllStudentResponse(Long id, String name, Date birthDate, RegisteredCourseDto course, Boolean active) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.course = course;
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public RegisteredCourseDto getCourse() {
        return course;
    }

    public void setCourse(RegisteredCourseDto course) {
        this.course = course;
    }
}
