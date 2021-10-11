package kr.pe.fourj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.StudentDTO;
import kr.pe.fourj.repository.StudentRepository;

@RestController
public class StudentController {

	public StudentController() {
		System.out.println("StudentController ---");
	}
	
	@Autowired
	private StudentRepository dao;
	
	@PostMapping("student")
	public String saveStudent(StudentDTO.Create student) {
		System.out.println(student.getId() + "학생 저장시도");
		Integer age = 3;
		dao.save(new Student(student.getId(), student.getPw(), student.getName(), student.getBirth(), age, student.getNickName(), student.getGender(), student.getAddress(), student.getPhone()));
	
		return "학생저장성공";
	}
}
