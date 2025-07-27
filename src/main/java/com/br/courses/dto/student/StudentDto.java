package com.br.courses.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record StudentDto(String name,
                         @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Sao_Paulo")
                         Date birthDate,
                         String zipCode) {

    @Override
    public String name() {
        return name;
    }

    @Override
    public Date birthDate() {
        return birthDate;
    }

    @Override
    public String zipCode() {
        return zipCode;
    }

}
