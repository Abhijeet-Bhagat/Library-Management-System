package com.lms.services;

import com.lms.models.Student;
import com.lms.repository.StudentRepository;
import com.lms.request.StudentCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    public void createStudent(StudentCreateRequest studentCreateRequest){
        Student student = studentCreateRequest.to();
        studentRepository.save(student);
    }

    public Student findStudentByStudentId(int studentId){
        Student student = studentRepository.findById(studentId).orElse(null);
        return student;
    }
}
