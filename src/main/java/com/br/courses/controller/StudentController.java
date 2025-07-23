package com.br.courses.controller;

import com.br.courses.dto.student.StudentDto;
import com.br.courses.dto.student.StudentResponseDto;
import com.br.courses.services.student.IStudentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private IStudentInterface iStudentInterface;

    @PostMapping
    public ResponseEntity<StudentResponseDto> registerStudent(@RequestBody StudentDto studentDto) {
        var student = iStudentInterface.registerStudent(studentDto);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<StudentResponseDto> registerForCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        var student = iStudentInterface.registerStudentInCourse(studentId, courseId);
        if (student == null)
            return ResponseEntity.notFound().build();
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        var students = iStudentInterface.getAllStudents();
        if (students == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getById(@PathVariable Long id) {
        var student = iStudentInterface.getById(id);
        if (student == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> modifyStudent(@PathVariable Long id,
                                                            @RequestBody StudentDto studentDto) {
        var student = iStudentInterface.modifyStudent(id, studentDto);
        if (student == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        try {
            iStudentInterface.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
