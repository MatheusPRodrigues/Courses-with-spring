package com.br.courses.services.course;

import com.br.courses.dto.course.CourseDto;
import com.br.courses.dto.course.CourseResponseDto;
import com.br.courses.dto.course.StudentInCourseDto;
import com.br.courses.model.Course;
import com.br.courses.model.Response;
import com.br.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseInterface {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Response<CourseResponseDto> registerCourse(CourseDto courseDto) {
        Response<CourseResponseDto> response = new Response<>();

        if (courseDto.name().isEmpty() || courseDto.name().isBlank()) {
            response.setMessage("Valor inválido inserido no campo name!");
            response.setCode(400);
            return response;
        }
        if (courseDto.courseLoad() <= 0) {
            response.setMessage("Valor inválido inserido no campo courseLoad!");
            response.setCode(400);
            return response;
        }
        Date dateNow = new Date();
        if (!courseDto.completionDate().after(dateNow)) {
            response.setMessage("Valor inválido inserido no campo completionDate!");
            response.setCode(400);
            return response;
        }

        Course course = new Course(
                courseDto.name(),
                courseDto.courseLoad(),
                courseDto.completionDate()
        );

        Course courseSaved = courseRepository.save(course);

        List<CourseResponseDto> courseResponseDtoList = new ArrayList<>();
        courseResponseDtoList.add(new CourseResponseDto(
                courseSaved.getId(),
                courseSaved.getName(),
                courseSaved.getCourseLoad(),
                courseSaved.getCompletionDate(),
                null,
                courseSaved.getActive()
        ));
        response.setMessage("Curso cadastrado com sucesso!");
        response.setData(courseResponseDtoList);
        response.setCode(201);
        return response;
    }

    @Override
    public Response<CourseResponseDto> modifyCourse(Long id, CourseDto courseDto) {
        Response<CourseResponseDto> response = new Response<>();

        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty() || !course.get().getActive()) {
            response.setMessage("Curso não econtrado!");
            response.setCode(404);
            return response;
        }
        if (courseDto.name().isEmpty() || courseDto.name().isBlank()) {
            response.setMessage("Valor inválido inserido no campo name!");
            response.setCode(400);
            return response;
        }
        if (courseDto.courseLoad() <= 0) {
            response.setMessage("Valor inválido inserido no campo courseLoad!");
            response.setCode(400);
            return response;
        }
        Date dateNow = new Date();
        if (!courseDto.completionDate().after(dateNow)) {
            response.setMessage("Valor inválido inserido no campo completionDate!");
            response.setCode(400);
            return response;
        }

        Course courseToModify = course.get();

        courseToModify.setName(courseDto.name());
        courseToModify.setCourseLoad(courseDto.courseLoad());
        courseToModify.setCompletionDate(courseDto.completionDate());

        Course courseSaved = courseRepository.save(courseToModify);

        List<CourseResponseDto> courseResponseDtoList = new ArrayList<>();
        courseResponseDtoList.add(new CourseResponseDto(
                courseSaved.getId(),
                courseSaved.getName(),
                courseSaved.getCourseLoad(),
                courseSaved.getCompletionDate(),
                new ArrayList<>(),
                courseSaved.getActive()
        ));
        response.setMessage("Course modificado com sucesso!");
        response.setData(courseResponseDtoList);
        response.setCode(200);
        return response;
    }

    @Override
    public Response<CourseResponseDto> findAllCourse() {
        Response<CourseResponseDto> response = new Response<>();

        List<Course> courses = courseRepository.findAll();

        List<CourseResponseDto> courseResponseDtoList = new ArrayList<>();

        for (var c : courses) {
            if (c.getActive()) {
                courseResponseDtoList.add(new CourseResponseDto(
                        c.getId(),
                        c.getName(),
                        c.getCourseLoad(),
                        c.getCompletionDate(),
                        new ArrayList<>(),
                        c.getActive()
                ));
            }
        }

        if (courses.isEmpty() || courseResponseDtoList.isEmpty()) {
            response.setMessage("Nenhum curso econtrado!");
            response.setCode(404);
            return response;
        }

        response.setMessage("Cursos exibidos com sucesso!");
        response.setData(courseResponseDtoList);
        response.setCode(200);
        return response;
    }

    @Override
    public Response<CourseResponseDto> findById(Long id) {
        Response<CourseResponseDto> response = new Response<>();

        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty() || !course.get().getActive()) {
            response.setMessage("Curso não encontrado!");
            response.setCode(404);
            return response;
        }

        List<StudentInCourseDto> studentInCourseDtos = new ArrayList<>();
        for (var s : course.get().getStudents()) {
            studentInCourseDtos.add(new StudentInCourseDto(
                    s.getId(),
                    s.getName()
            ));
        }

        List<CourseResponseDto> courseResponseDtoList = new ArrayList<>();
        courseResponseDtoList.add(new CourseResponseDto(
                course.get().getId(),
                course.get().getName(),
                course.get().getCourseLoad(),
                course.get().getCompletionDate(),
                new ArrayList<>(studentInCourseDtos),
                course.get().getActive()
        ));
        response.setMessage("Curso encontrado com sucesso!");
        response.setData(courseResponseDtoList);
        response.setCode(200);
        return response;
    }

    @Override
    public Response<Void> inactiveCourse(Long id) {
        Response<Void> response = new Response<>();

        Optional<Course> courseToSelect = courseRepository.findById(id);
        if (courseToSelect.isEmpty()) {
            response.setMessage("Curso não encontrado!");
            response.setCode(404);
            return response;
        }
        if (!courseToSelect.get().getActive()) {
            response.setMessage("Este curso já está desativado!");
            response.setCode(400);
            return response;
        }

        Course course = courseToSelect.get();

        for (var student : course.getStudents()) {
            student.setCourse(null);
        }

        course.setActive();
        courseRepository.save(course);
        response.setMessage("Curso desativado com sucesso!");
        response.setCode(200);
        return response;
    }

    @Override
    public Response<CourseResponseDto> getAllInactiveCourses() {
        Response<CourseResponseDto> response = new Response<>();

        List<Course> courseList = courseRepository.findAll();
        if (courseList.isEmpty() || courseList.stream().allMatch(Course::getActive)) {
            response.setMessage("Não cursos desativados!");
            response.setCode(404);
            return response;
        }

        List<CourseResponseDto> courseResponseDtoList = new ArrayList<>();
        for (var c : courseList) {
            if (!c.getActive()) {
                courseResponseDtoList.add(new CourseResponseDto(
                        c.getId(),
                        c.getName(),
                        c.getCourseLoad(),
                        c.getCompletionDate(),
                        null,
                        c.getActive()
                ));
            }
        }
        response.setMessage("Cursos inativos exibidos com sucesso!");
        response.setData(courseResponseDtoList);
        response.setCode(200);
        return response;
    }

    @Override
    public Response<CourseResponseDto> activeCourse(Long id) {
        Response<CourseResponseDto> response = new Response<>();

        Optional<Course> courseSearch = courseRepository.findById(id);
        if (courseSearch.isEmpty() || courseSearch.get().getActive()) {
            response.setMessage("Curso inativo não localizado!");
            response.setCode(404);
            return response;
        }

        Course courseToActive = courseSearch.get();
        courseToActive.setActive();
        Course courseActive = courseRepository.save(courseToActive);

        List<CourseResponseDto> courseResponseDtoList = new ArrayList<>();
        courseResponseDtoList.add(new CourseResponseDto(
                courseActive.getId(),
                courseActive.getName(),
                courseActive.getCourseLoad(),
                courseActive.getCompletionDate(),
                new ArrayList<>(),
                courseActive.getActive()
        ));
        response.setMessage("Curso ativado com sucesso!");
        response.setData(courseResponseDtoList);
        response.setCode(200);
        return response;
    }

    @Override
    public Response<Void> deleteCourse(Long id) {
        Response<Void> response = new Response<>();

        Optional<Course> courseSearch = courseRepository.findById(id);
        if (courseSearch.isEmpty()) {
            response.setMessage("Nenhum curso encontrado!");
            response.setCode(404);
            return response;
        }
        if (courseSearch.get().getActive()) {
            response.setMessage("O curso precisa estar inativo para poder ser excluído!");
            response.setCode(400);
            return response;
        }

        Course courseToDelete = courseSearch.get();
        courseRepository.delete(courseToDelete);
        return response;
    }
}
