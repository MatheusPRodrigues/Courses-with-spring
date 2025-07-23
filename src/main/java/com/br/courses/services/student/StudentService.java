package com.br.courses.services.student;

import com.br.courses.dto.student.RegisteredCourseDto;
import com.br.courses.dto.student.StudentDto;
import com.br.courses.dto.student.StudentResponseDto;
import com.br.courses.model.Course;
import com.br.courses.model.Student;
import com.br.courses.repository.CourseRepository;
import com.br.courses.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IStudentInterface {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;

    @Override
    public StudentResponseDto registerStudent(StudentDto studentDto) {
        Student studentToSave = new Student(
                studentDto.name(),
                studentDto.birthDate()
        );

        Student studentSaved = studentRepository.save(studentToSave);

        return new StudentResponseDto(
                studentSaved.getId(),
                studentSaved.getName(),
                studentSaved.getBirthDate(),
                null);
    }

    @Override
    public List<StudentResponseDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        List<StudentResponseDto> listStudentsResponse = new ArrayList<>();

        for (var s : students) {
            StudentResponseDto studentResponseDto = new StudentResponseDto(
                    s.getId(),
                    s.getName(),
                    s.getBirthDate(),
                    null);
            if (s.getCourse() != null)
                studentResponseDto.setCourse(
                        new RegisteredCourseDto(
                                s.getCourse().getId(),
                                s.getCourse().getName(),
                                s.getCourse().getCourseLoad(),
                                s.getCourse().getCompletionDate()));
            listStudentsResponse.add(studentResponseDto);
        }

        return listStudentsResponse;
    }

    @Override
    public StudentResponseDto getById(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty())
            return null;

        Student studentSelected = student.get();

        StudentResponseDto studentResponseDto = new StudentResponseDto(
                studentSelected.getId(),
                studentSelected.getName(),
                studentSelected.getBirthDate(),
                null);

        if (studentSelected.getCourse() != null)
            studentResponseDto.setCourse(
                    new RegisteredCourseDto(
                            studentSelected.getCourse().getId(),
                            studentSelected.getCourse().getName(),
                            studentSelected.getCourse().getCourseLoad(),
                            studentSelected.getCourse().getCompletionDate()
                    ));

        return studentResponseDto;
    }

    @Override
    public StudentResponseDto registerStudentInCourse(Long studentId, Long courseId) {
        Optional<Student> studentSelected = studentRepository.findById(studentId);
        if (studentSelected.isEmpty())
            return null;

        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty())
            return null;

        Student student = studentSelected.get();
        student.setCourse(course.get());

        studentRepository.save(student);

        return new StudentResponseDto(
                student.getId(),
                student.getName(),
                student.getBirthDate(),
                new RegisteredCourseDto(
                        student.getCourse().getId(),
                        student.getCourse().getName(),
                        student.getCourse().getCourseLoad(),
                        student.getCourse().getCompletionDate()
                )
        );
    }

    @Override
    public StudentResponseDto modifyStudent(Long id, StudentDto studentDto) {
        Optional<Student> studentSelected = studentRepository.findById(id);

        if (studentSelected.isEmpty())
            return null;

        Student student = studentSelected.get();

        student.setName(studentDto.name());
        student.setBirthDate(studentDto.birthDate());

        Student studentSaved = studentRepository.save(student);
        StudentResponseDto studentResponseDto = new StudentResponseDto(
                studentSaved.getId(),
                studentSaved.getName(),
                studentSaved.getBirthDate(),
                null);

        if (studentSaved.getCourse() != null) {
            studentResponseDto.setCourse(
                    new RegisteredCourseDto(
                            studentSaved.getCourse().getId(),
                            studentSaved.getCourse().getName(),
                            studentSaved.getCourse().getCourseLoad(),
                            studentSaved.getCourse().getCompletionDate()
                    ));
        }

        return studentResponseDto;
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id))
            throw new RuntimeException("Curso não encontrado!");

        studentRepository.deleteById(id);
    }
}
