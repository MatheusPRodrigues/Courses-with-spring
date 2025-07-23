package com.br.courses.services.student;

import com.br.courses.dto.student.StudentDto;
import com.br.courses.dto.student.StudentResponseDto;

import java.util.List;

public interface IStudentInterface {

    StudentResponseDto registerStudent(StudentDto studentDto);
    List<StudentResponseDto> getAllStudents();
    StudentResponseDto getById(Long id);
    StudentResponseDto registerStudentInCourse(Long studentId, Long courseId);
    StudentResponseDto modifyStudent(Long id, StudentDto studentDto);
    void deleteStudent(Long id);

}
