package com.br.courses.dto.student;

import com.br.courses.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class StudentResponse {

    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private Address address;
    private RegisteredCourseDto course;
    private Boolean active;

    public StudentResponse(Long id, String name, Date birthDate, Address address, RegisteredCourseDto course, Boolean active) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public RegisteredCourseDto getCourse() {
        return course;
    }

    public void setCourse(RegisteredCourseDto course) {
        this.course = course;
    }
}
