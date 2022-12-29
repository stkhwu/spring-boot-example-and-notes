package com.vtxlab.demo.student.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vtxlab.demo.student.entity.Student;
import com.vtxlab.demo.student.repository.StudentRepository;
import com.vtxlab.demo.student.service.StudentService;

@Service // Bean collegeStudentService -> Spring Container
public class CollegeStudentService implements StudentService {

  @Autowired // if no qualifier and there is only one clase implements the interface 
  // the object reference name is not important for finding the bean
  StudentRepository studentRepository;

  @Autowired // there is qualifier, Qualifier value = bean name
  @Qualifier(value = "BeanForAlan")
  Student studentAlan;


  @Override
  public List<Student> findStudents() {
    return studentRepository.findAll();
  }

  @Override
  public void createStudent(String name, Double height) {
    studentRepository.createStudent(name, height);
  }
}
