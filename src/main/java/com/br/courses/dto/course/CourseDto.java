package com.br.courses.dto.course;

import java.util.Date;

public record CourseDto(String name, Long courseLoad, Date completionDate) {

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
