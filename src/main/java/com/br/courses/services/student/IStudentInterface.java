package com.br.courses.services.student;

import com.br.courses.dto.student.AllStudentResponse;
import com.br.courses.dto.student.StudentDto;
import com.br.courses.dto.student.StudentResponse;
import com.br.courses.model.Response;

public interface IStudentInterface {

    Response<StudentResponse> registerStudent(StudentDto studentDto);
    Response<AllStudentResponse> getAllStudents();
    Response<StudentResponse> getById(Long id);
    Response<StudentResponse> registerStudentInCourse(Long studentId, Long courseId);
    Response<StudentResponse> modifyStudent(Long id, StudentDto studentDto);
    Response<Void> inactiveStudent(Long id);
    Response<AllStudentResponse> getAllInactiveStudents();
    Response<StudentResponse> activeStudent(Long id);
    Response<Void> deleteStudent(Long id);

}
