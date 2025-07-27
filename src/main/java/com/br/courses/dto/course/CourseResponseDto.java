package com.br.courses.dto.course;

import com.br.courses.model.Student;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public record CourseResponseDto(Long id,
                                String name,
                                Long courseLoad,
                                @JsonFormat(pattern = "yyyy-MM-dd")
                                Date completionDate,
                                List<StudentInCourseDto> students,
                                Boolean active) {

    @Override
    public Boolean active() {
        return active;
    }

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

    @Override
    public List<StudentInCourseDto> students() {
        return students;
    }
}
