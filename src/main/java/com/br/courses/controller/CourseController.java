package com.br.courses.controller;

import com.br.courses.dto.course.CourseDto;
import com.br.courses.dto.course.CourseResponseDto;
import com.br.courses.model.Response;
import com.br.courses.services.course.ICourseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private ICourseInterface iCourseInterface;

    @PostMapping
    public ResponseEntity<Response<CourseResponseDto>> registerCourse(@RequestBody CourseDto courseDto) {
        var response = iCourseInterface.registerCourse(courseDto);
        if (response.getCode() == 400)
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<CourseResponseDto>> modifyCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        var response = iCourseInterface.modifyCourse(id, courseDto);
        if (response.getCode() == 404)
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        if (response.getCode() == 400)
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<Response<CourseResponseDto>> findAllCourses() {
        var response = iCourseInterface.findAllCourse();
        if (response.getData().isEmpty())
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CourseResponseDto>> findById(@PathVariable Long id) {
        var response = iCourseInterface.findById(id);
        if (response.getData().isEmpty())
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> inactiveCourse(@PathVariable Long id) {
        var response = iCourseInterface.inactiveCourse(id);
        if (response.getCode() == 404)
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/inactives")
    public ResponseEntity<Response<CourseResponseDto>> getAllInactiveCourses() {
        var response = iCourseInterface.getAllInactiveCourses();
        if (response.getCode() == 404)
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/active/{id}")
    public ResponseEntity<Response<CourseResponseDto>> activeCourse(@PathVariable Long id) {
        var response = iCourseInterface.activeCourse(id);
        if (response.getCode() == 404)
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<Void>> deleteCourse(@PathVariable Long id) {
        var response = iCourseInterface.deleteCourse(id);
        if (response.getCode() == 404)
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        if (response.getCode() == 400)
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }
}
