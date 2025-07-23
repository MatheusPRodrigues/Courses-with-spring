package com.br.courses.dto.student;

import java.util.Date;

public record StudentDto(String name, Date birthDate) {

    @Override
    public String name() {
        return name;
    }

    @Override
    public Date birthDate() {
        return birthDate;
    }
}
