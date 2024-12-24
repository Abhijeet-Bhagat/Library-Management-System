package com.lms.controller;

import com.lms.request.BookCreateRequest;
import com.lms.request.StudentCreateRequest;
import com.lms.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/student")
    public void createStudent(@Valid @RequestBody StudentCreateRequest studentCreateRequest){ //@Valid annotation is required for the validations put in BookCreateRequest to work
        studentService.createStudent(studentCreateRequest);
        System.out.println("Student created successfully");
    }
}
