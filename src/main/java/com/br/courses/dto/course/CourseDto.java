package com.br.courses.dto.course;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record CourseDto(String name, Long courseLoad,
                        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Sao_Paulo")
                        Date completionDate) {

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
