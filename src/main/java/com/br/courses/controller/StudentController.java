package com.br.courses.controller;

import com.br.courses.dto.student.AllStudentResponse;
import com.br.courses.dto.student.StudentDto;
import com.br.courses.dto.student.StudentResponse;
import com.br.courses.model.Response;
import com.br.courses.services.student.IStudentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private IStudentInterface iStudentInterface;

    @PostMapping
    public ResponseEntity<Response<StudentResponse>> registerStudent(@RequestBody StudentDto studentDto) {
        var response = iStudentInterface.registerStudent(studentDto);
        if (response.getCode() == 502)
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        if (response.getCode() == 400)
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Response<StudentResponse>> registerForCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        var response = iStudentInterface.registerStudentInCourse(studentId, courseId);
        if (response.getData().isEmpty())
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<AllStudentResponse>> getAllStudents() {
        var response = iStudentInterface.getAllStudents();
        if (response.getData().isEmpty()) {
            response.setMessage("Nenhum estudante encontrado!");
            response.setCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<StudentResponse>> getById(@PathVariable Long id) {
        var response = iStudentInterface.getById(id);
        if (response.getData().isEmpty())
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<StudentResponse>> modifyStudent(@PathVariable Long id,
                                                         @RequestBody StudentDto studentDto) {
        var response = iStudentInterface.modifyStudent(id, studentDto);
        if (response.getCode() == 404)
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        if (response.getCode() == 400)
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        if (response.getCode() == 502)
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> desactiveStudent(@PathVariable Long id) {
        var response = iStudentInterface.inactiveStudent(id);
        if (response.getCode() == 404)
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/inactives")
    public ResponseEntity<Response<AllStudentResponse>> getAllDesactiveStudents() {
        var response = iStudentInterface.getAllInactiveStudents();
        if (response.getCode() == 404)
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/active/{id}")
    public ResponseEntity<Response<StudentResponse>> activeStudent(@PathVariable Long id) {
        var response = iStudentInterface.activeStudent(id);
        if (response.getCode() == 404)
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        if (response.getCode() == 400)
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<Void>> deleteStudent(@PathVariable Long id) {
        var response = iStudentInterface.deleteStudent(id);
        if (response.getCode() == 404)
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        if (response.getCode() == 400)
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

}
