package com.br.courses.dto.course;

public record StudentInCourseDto(Long id, String name) {

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }
}
