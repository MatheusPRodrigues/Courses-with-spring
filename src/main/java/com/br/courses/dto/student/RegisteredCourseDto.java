package com.br.courses.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record RegisteredCourseDto(
        Long id,
        String name,
        Long courseLoad,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date completionDate) {

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Long courseLoad() {
        return courseLoad;
    }

    @Override
    public Date completionDate() {
        return completionDate;
    }
}
