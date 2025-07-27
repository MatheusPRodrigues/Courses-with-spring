package com.br.courses.services.course;

import com.br.courses.dto.course.CourseDto;
import com.br.courses.dto.course.CourseResponseDto;
import com.br.courses.model.Response;

public interface ICourseInterface {

    Response<CourseResponseDto> registerCourse(CourseDto courseDto);
    Response<CourseResponseDto> modifyCourse(Long id, CourseDto courseDto);
    Response<CourseResponseDto> findAllCourse();
    Response<CourseResponseDto> findById(Long id);
    Response<Void> inactiveCourse(Long id);
    Response<CourseResponseDto> getAllInactiveCourses();
    Response<CourseResponseDto> activeCourse(Long id);
    Response<Void> deleteCourse(Long id);

}
