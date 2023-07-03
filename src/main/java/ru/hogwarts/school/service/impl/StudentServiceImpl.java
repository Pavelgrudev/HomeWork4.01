package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.EntityNotFoundException;
import ru.hogwarts.school.exception.IncorrectArgumentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student add(Student student) {
        return repository.save(student);
    }

    @Override
    public Student remove(Long id) {
        Student student = get(id);
        repository.deleteById(id);
        return student;
    }

    @Override
    public Student update(Student student) {
        Student existedStudent = get(student.getId());
        return repository.save(student);
    }

    @Override
    public Collection<Student> getAll() {
        return repository.findAll();
    }

    @Override
    public Student get(Long id) {
        Optional<Student> student = repository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Collection<Student> getByAge(Integer startAge, Integer endAge) {
        checkAge(startAge);
        checkAge(endAge);
        return repository.findStudentByAgeBetween(startAge, endAge);
    }

    @Override
    public Integer getStudentsCount() {
        return repository.getCount();
    }

    @Override
    public Float getStudentsAverageAge() {
        return repository.getAverageAge();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        return repository.getLastFive();
    }

    private void checkAge(Integer age) {
        if (age <= 10 || age >= 90) {
            throw new IncorrectArgumentException("некоректный возраст студента");
        }
    }


}
