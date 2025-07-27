package com.br.courses.services.student;

import com.br.courses.dto.address.AddressResponseDto;
import com.br.courses.dto.student.AllStudentResponse;
import com.br.courses.dto.student.RegisteredCourseDto;
import com.br.courses.dto.student.StudentDto;
import com.br.courses.dto.student.StudentResponse;
import com.br.courses.model.Address;
import com.br.courses.model.Course;
import com.br.courses.model.Response;
import com.br.courses.model.Student;
import com.br.courses.repository.AddressRepository;
import com.br.courses.repository.CourseRepository;
import com.br.courses.repository.StudentRepository;
import com.br.courses.services.external.viacep.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements IStudentInterface {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ViaCepService viaCepService;

    @Override
    public Response<StudentResponse> registerStudent(StudentDto studentDto) {
        Response<StudentResponse> response = new Response<>();

        if (studentDto.name().isEmpty() || studentDto.name().isBlank()) {
            response.setMessage("Valor inválido inserido no campo name!");
            response.setCode(400);
            return response;
        }
        Date dateNow = new Date();
        if (!studentDto.birthDate().before(dateNow)) {
            response.setMessage("Valor inválido inserido no campo birthDate!");
            response.setCode(400);
            return response;
        }

        String zipCode = studentDto.zipCode();
        Optional<Address> addressSelected = addressRepository.findById(zipCode);

        Address address;
        if (addressSelected.isEmpty()) {
            try {
                AddressResponseDto responseDto = viaCepService.getAddress(zipCode);
                if (responseDto.cep() == null) {
                    response.setMessage("Erro ao localizar seu CEP, tente novamente mais tarde!");
                    response.setCode(502);
                    return response;
                }

                Address newAddress = new Address(responseDto.cep(), responseDto.logradouro(), responseDto.complemento(), responseDto.bairro(), responseDto.localidade(), responseDto.uf());

                address = addressRepository.save(newAddress);
            } catch (HttpClientErrorException e) {
                response.setMessage("CEP inválido!");
                response.setCode(400);
                return response;
            }

        } else {
            address = addressSelected.get();
        }

        Student student = new Student(studentDto.name(), studentDto.birthDate(), address);

        Student studentSaved = studentRepository.save(student);

        StudentResponse studentResponse = new StudentResponse(studentSaved.getId(), studentSaved.getName(), studentSaved.getBirthDate(), studentSaved.getAddress(), null, studentSaved.getActive());

        List<StudentResponse> responseList = new ArrayList<>();
        responseList.add(studentResponse);

        response.setMessage("Usuário cadastrado com sucesso!");
        response.setData(responseList);
        response.setCode(201);

        return response;
    }

    @Override
    public Response<AllStudentResponse> getAllStudents() {
        Response<AllStudentResponse> response = new Response<>();

        List<Student> students = studentRepository.findAll();

        List<AllStudentResponse> listStudentsResponse = new ArrayList<>();

        for (var s : students) {
            if (s.getActive()) {
                AllStudentResponse student = new AllStudentResponse(s.getId(), s.getName(), s.getBirthDate(), null, s.getActive());
                if (s.getCourse() != null) {
                    student.setCourse(new RegisteredCourseDto(s.getCourse().getId(), s.getCourse().getName(), s.getCourse().getCourseLoad(), s.getCourse().getCompletionDate()));
                }
                listStudentsResponse.add(student);
            }
        }

        response.setMessage("Estudantes listados com sucesso!");
        response.setData(listStudentsResponse);
        response.setCode(200);
        return response;
    }

    @Override
    public Response<StudentResponse> getById(Long id) {
        Response<StudentResponse> response = new Response<>();
        List<StudentResponse> students = new ArrayList<>();

        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty() || !student.get().getActive()) {
            response.setMessage("Estudante não localizado!");
            response.setCode(404);
            return response;
        }

        Student studentSelected = student.get();

        StudentResponse studentResponse = new StudentResponse(studentSelected.getId(), studentSelected.getName(), studentSelected.getBirthDate(), studentSelected.getAddress(), null, studentSelected.getActive());

        if (studentSelected.getCourse() != null)
            studentResponse.setCourse(new RegisteredCourseDto(studentSelected.getCourse().getId(), studentSelected.getCourse().getName(), studentSelected.getCourse().getCourseLoad(), studentSelected.getCourse().getCompletionDate()));

        students.add(studentResponse);

        response.setMessage("Estudante localizado com sucesso!");
        response.setData(students);
        response.setCode(200);
        return response;
    }

    @Override
    public Response<StudentResponse> registerStudentInCourse(Long studentId, Long courseId) {
        Response<StudentResponse> response = new Response<>();
        List<StudentResponse> studentResponseList = new ArrayList<>();

        Optional<Student> studentSelected = studentRepository.findById(studentId);
        if (studentSelected.isEmpty() || !studentSelected.get().getActive()) {
            response.setMessage("Estudante não encontrado!");
            response.setCode(404);
            return response;
        }

        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()) {
            response.setMessage("Curso não encontrado!");
            response.setCode(404);
            return response;
        }

        Student student = studentSelected.get();
        student.setCourse(course.get());

        Student studentSaved = studentRepository.save(student);

        studentResponseList.add(new StudentResponse(studentSaved.getId(), studentSaved.getName(), studentSaved.getBirthDate(), studentSaved.getAddress(), new RegisteredCourseDto(studentSaved.getCourse().getId(), studentSaved.getCourse().getName(), studentSaved.getCourse().getCourseLoad(), studentSaved.getCourse().getCompletionDate()), studentSaved.getActive()));

        response.setMessage("Aluno cadastrado no curso com sucesso!");
        response.setData(studentResponseList);
        response.setCode(201);
        return response;
    }

    @Override
    public Response<StudentResponse> modifyStudent(Long id, StudentDto studentDto) {
        Response<StudentResponse> response = new Response<>();
        List<StudentResponse> studentResponseList = new ArrayList<>();
        Address address;

        Optional<Student> studentSelected = studentRepository.findById(id);

        if (studentSelected.isEmpty() || !studentSelected.get().getActive()) {
            response.setMessage("Estudante não encontrado!");
            response.setCode(404);
            return response;
        }
        if (studentDto.name().isEmpty() || studentDto.name().isBlank()) {
            response.setMessage("Valor inválido inserido no campo name!");
            response.setCode(400);
            return response;
        }
        Date dateNow = new Date();
        if (!studentDto.birthDate().before(dateNow)) {
            response.setMessage("Valor inválido inserido no campo birthDate!");
            response.setCode(400);
            return response;
        }

        Student student = studentSelected.get();

        student.setName(studentDto.name());
        student.setBirthDate(studentDto.birthDate());

        if (!student.getAddress().getZipCode().equals(studentDto.zipCode())) {
            try {
                Optional<Address> addressSelected = addressRepository.findById(studentDto.zipCode());
                if (addressSelected.isEmpty()) {
                    try {
                        AddressResponseDto addressResponseDto = viaCepService.getAddress(studentDto.zipCode());
                        if (addressResponseDto.cep() == null) {
                            response.setMessage("Erro ao localizar seu CEP, tente novamente mais tarde!");
                            response.setCode(502);
                            return response;
                        }
                        Address newAddress = new Address(
                                addressResponseDto.cep(),
                                addressResponseDto.logradouro(),
                                addressResponseDto.complemento(),
                                addressResponseDto.bairro(),
                                addressResponseDto.localidade(),
                                addressResponseDto.uf()
                        );
                        address = addressRepository.save(newAddress);
                    } catch (HttpClientErrorException e) {
                        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
                    }
                } else
                    address = addressSelected.get();
            } catch (HttpClientErrorException e) {
                response.setMessage("CEP inválido!");
                response.setCode(400);
                return response;
            }
            student.setAddress(address);
        }

        Student studentSaved = studentRepository.save(student);

        StudentResponse studentResponse = new StudentResponse(studentSaved.getId(), studentSaved.getName(), studentSaved.getBirthDate(), studentSaved.getAddress(), null, studentSaved.getActive());

        if (studentSaved.getCourse() != null) {
            studentResponse.setCourse(new RegisteredCourseDto(studentSaved.getCourse().getId(), studentSaved.getCourse().getName(), studentSaved.getCourse().getCourseLoad(), studentSaved.getCourse().getCompletionDate()));
        }

        studentResponseList.add(studentResponse);
        response.setMessage("Dados do estudante alterados com sucesso!");
        response.setData(studentResponseList);
        response.setCode(200);
        return response;
    }

    @Override
    public Response<Void> inactiveStudent(Long id) {
        Response<Void> response = new Response<>();
        Optional<Student> studentSelected = studentRepository.findById(id);

        if (studentSelected.isEmpty() || !studentSelected.get().getActive()) {
            response.setMessage("Estudante não encontrado!");
            response.setCode(404);
            return response;
        }

        Student student = studentSelected.get();
        student.setCourse(null);
        student.setActive();
        studentRepository.save(student);

        response.setMessage("Estudante desativado com sucesso!");
        response.setCode(200);
        return response;
    }

    @Override
    public Response<AllStudentResponse> getAllInactiveStudents() {
        Response<AllStudentResponse> response = new Response<>();
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty() || students.stream().allMatch(Student::getActive)) {
            response.setMessage("Nenhum estudante inativo!");
            response.setCode(404);
            return response;

        }
        List<AllStudentResponse> studentResponseList = new ArrayList<>();
        for (var s : students) {
            if (!s.getActive()) {
                studentResponseList.add(new AllStudentResponse(
                        s.getId(),
                        s.getName(),
                        s.getBirthDate(),
                        null,
                        s.getActive()
                ));
            }
        }
        response.setMessage("Exibindo estudantes desativados!");
        response.setData(studentResponseList);
        response.setCode(200);
        return response;
    }

    @Override
    public Response<StudentResponse> activeStudent(Long id) {
        Response<StudentResponse> response = new Response<>();
        Optional<Student> studentSearch = studentRepository.findById(id);
        if (studentSearch.isEmpty()) {
            response.setMessage("Estudante não encontrado!");
            response.setCode(404);
            return response;
        }

        Student student = studentSearch.get();
        if (student.getActive()) {
            response.setMessage("Esse estudante já está como ativo no sistema!");
            response.setCode(400);
            return response;
        }

        student.setActive();
        Student studentSaved = studentRepository.save(student);
        List<StudentResponse> studentResponseList = new ArrayList<>();
        studentResponseList.add(new StudentResponse(
                studentSaved.getId(),
                studentSaved.getName(),
                studentSaved.getBirthDate(),
                studentSaved.getAddress(),
                null,
                studentSaved.getActive()
        ));
        response.setMessage("Estudante ativado novamente!");
        response.setData(studentResponseList);
        response.setCode(200);
        return response;
    }

    @Override
    public Response<Void> deleteStudent(Long id) {
        Response<Void> response = new Response<>();

        Optional<Student> studentSearch = studentRepository.findById(id);
        if (studentSearch.isEmpty()) {
            response.setMessage("Estudante não encontrado!");
            response.setCode(404);
            return response;
        }
        if (studentSearch.get().getActive()) {
            response.setMessage("Estudante precisa estar inativo");
            response.setCode(400);
            return response;
        }

        Student studentToDelete = studentSearch.get();
        studentToDelete.setAddress(null);
        studentRepository.delete(studentToDelete);
        return response;
    }
}
