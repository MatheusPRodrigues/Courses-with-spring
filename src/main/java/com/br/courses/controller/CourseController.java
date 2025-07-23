package com.br.courses.controller;

import com.br.courses.dto.course.CourseDto;
import com.br.courses.dto.course.CourseResponseDto;
import com.br.courses.services.course.CourseService;
import com.br.courses.services.course.ICourseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private ICourseInterface iCourseInterface;

    @PostMapping
    public ResponseEntity<CourseResponseDto> registerCourse(@RequestBody CourseDto courseDto) {
        var newCourse = iCourseInterface.registerCourse(courseDto);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> modifyCourse(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        var modifiedCourse = iCourseInterface.modifyCourse(id, courseDto);
        if (modifiedCourse == null)
            return ResponseEntity.notFound().build();
        return new ResponseEntity<>(modifiedCourse, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> findAllCourses() {
        var courses = iCourseInterface.findAllCourse();
        if (courses.isEmpty())
            return ResponseEntity.notFound().build();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> findById(@PathVariable Long id) {
        var course = iCourseInterface.findById(id);

        if (course == null)
            return ResponseEntity.notFound().build();

        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        try {
            iCourseInterface.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
