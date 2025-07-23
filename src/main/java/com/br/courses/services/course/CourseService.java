package com.br.courses.services.course;

import com.br.courses.dto.course.CourseDto;
import com.br.courses.dto.course.CourseResponseDto;
import com.br.courses.dto.course.StudentInCourseDto;
import com.br.courses.model.Course;
import com.br.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseInterface {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CourseResponseDto registerCourse(CourseDto courseDto) {
        Course course = new Course(
                courseDto.name(),
                courseDto.courseLoad(),
                courseDto.completionDate()
        );

        Course courseSaved = courseRepository.save(course);

        return new CourseResponseDto(
                courseSaved.getId(),
                courseSaved.getName(),
                courseSaved.getCourseLoad(),
                courseSaved.getCompletionDate(),
                new ArrayList<>()
        );
    }

    @Override
    public CourseResponseDto modifyCourse(Long id, CourseDto courseDto) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) return null;

        Course courseToModify = course.get();

        courseToModify.setName(courseDto.name());
        courseToModify.setCourseLoad(courseDto.courseLoad());
        courseToModify.setCompletionDate(courseDto.completionDate());

        Course courseSaved = courseRepository.save(courseToModify);

        return new CourseResponseDto(
                courseSaved.getId(),
                courseSaved.getName(),
                courseSaved.getCourseLoad(),
                courseSaved.getCompletionDate(),
                new ArrayList<>()
        );
    }

    @Override
    public List<CourseResponseDto> findAllCourse() {
        List<Course> courses = courseRepository.findAll();

        List<CourseResponseDto> courseResponseDtos = new ArrayList<>();

        for (var c : courses) {
            courseResponseDtos.add(new CourseResponseDto(
                    c.getId(),
                    c.getName(),
                    c.getCourseLoad(),
                    c.getCompletionDate(),
                    new ArrayList<>()
            ));
        }

        return courseResponseDtos;
    }

    @Override
    public CourseResponseDto findById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty())
            return null;

        List<StudentInCourseDto> studentInCourseDtos = new ArrayList<>();
        for (var s : course.get().getStudents()) {
            studentInCourseDtos.add(new StudentInCourseDto(
                    s.getId(),
                    s.getName()
            ));
        }

        return new CourseResponseDto(
                course.get().getId(),
                course.get().getName(),
                course.get().getCourseLoad(),
                course.get().getCompletionDate(),
                new ArrayList<>(studentInCourseDtos)
        );
    }

    @Override
    public void deleteCourse(Long id) {
        Optional<Course> courseToSelect = courseRepository.findById(id);
        if (courseToSelect.isEmpty()) {
            throw new RuntimeException("Curso não encontrado!");
        }

        Course course = courseToSelect.get();

        for (var student : course.getStudents()) {
            student.setCourse(null);
        }

        courseRepository.deleteById(id);
    }
}
