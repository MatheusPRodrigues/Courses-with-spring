package com.br.courses.services.course;

import com.br.courses.dto.course.CourseDto;
import com.br.courses.dto.course.CourseResponseDto;

import java.util.List;

public interface ICourseInterface {

    CourseResponseDto registerCourse(CourseDto courseDto);
    CourseResponseDto modifyCourse(Long id, CourseDto courseDto);
    List<CourseResponseDto> findAllCourse();
    CourseResponseDto findById(Long id);
    void deleteCourse(Long id);

}
