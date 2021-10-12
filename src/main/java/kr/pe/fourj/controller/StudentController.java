package kr.pe.fourj.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.StudentDTO;
import kr.pe.fourj.repository.StudentRepository;

@RestController
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	//학생 저장
	@PostMapping("/student")
	public String saveStudent(@RequestBody StudentDTO.Create dto) {
		System.out.println("-- 학생 저장시도 --");
		Calendar current = new GregorianCalendar();
		Calendar birth = new GregorianCalendar();
		
		birth.setTime(dto.getBirth());
		current.setTime(new Date());
		
		int age = current.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + 1;
		
		studentRepository.save(new Student(dto.getId(), dto.getPw(), 
										   dto.getName(), dto.getBirth(), age, 
										   dto.getNickName(), dto.getGender(), 
										   dto.getAddress(), dto.getPhone()));

		return "학생저장성공! 나이는" + age + "살입니다.";
	}

	//학생 수정
	@PutMapping("/student")
	public Student updateStudent(@RequestBody StudentDTO.Update dto) {
		System.out.println("-- 학생 수정 시도 --");
		Student student = studentRepository.findById(dto.getIdx()).get();
		
		student.setNickName(dto.getNickName());
		student.setAddress(dto.getAddress());
		student.setPhone(dto.getPhone());

		studentRepository.save(student);

		return student;
	}

	//학생 삭제
	@DeleteMapping("/student")
	public String deleteStudent(@RequestBody StudentDTO.Delete dto) {
		System.out.println("-- 학생 삭제 시도 --");
		
		studentRepository.deleteById(dto.getIdx());

		return dto.getIdx() + "번 삭제성공";
	}

	//학생 단일 검색
	@GetMapping("/student")
	public Student findOne(@RequestBody StudentDTO.Get dto){
		System.out.println("-- 학생 단일 검색 시도 --");
		
		return studentRepository.findById(dto.getIdx()).get();
	}

	//학생 리스트 전체 검색
	@GetMapping("/studentall")
	public List<Student> findAll(){
		System.out.println("-- 학생 리스트 전체 검색 시도 --");
		List<Student> studentList = new ArrayList<Student>();
		
		studentRepository.findAll().forEach(e -> studentList.add(e));
		
		return studentList;
	}
	
	
}
