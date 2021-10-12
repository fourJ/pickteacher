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
import org.springframework.web.bind.annotation.RequestParam;
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
	private StudentRepository studentRepository;
	
	//학생 저장
	@PostMapping("student")
	public String saveStudent(@RequestBody StudentDTO.Create student) {
		System.out.println(student.getName() + "학생 저장시도");

		Calendar current = new GregorianCalendar();
		Calendar birth = new GregorianCalendar();
		
		birth.setTime(student.getBirth());
		current.setTime(new Date());
		
		int age = current.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + 1;
		
		studentRepository.save(new Student(student.getId(), student.getPw(), student.getName(), student.getBirth(),age, student.getNickName(), student.getGender(), student.getAddress(), student.getPhone()));
		
		return "학생저장성공! 나이는" + age + "살입니다.";
	}
	
	//전체 학생보기
	@GetMapping("studentlist")
	public List<Student> findAllStudent(){
		System.out.println("전체 학생 검색 시도");
		List<Student> studentList = new ArrayList<Student>();
		studentRepository.findAll().forEach(e -> studentList.add(e));
		
		System.out.println(studentList);
		return studentList;
	}
	//개인 학생보기
	@GetMapping("studentone")
	public List<Student> findOneStudent(@RequestParam String name){
		System.out.println("학생 이름으로 조회");
		List<Student> studentList = new ArrayList<Student>();
		studentRepository.findStudentByName(name).forEach(e -> studentList.add(e));
		
		return studentList;
	}
	
	//학생 삭제
	@DeleteMapping("student")
	public String deleteById(@RequestBody StudentDTO.Delete student) {
		System.out.println(student.getIdx() + "삭제 시도");
		studentRepository.deleteById(student.getIdx());
		
		return student.getIdx() + "번 삭제성공";
	}
	
	//학생 정보변경
	@PutMapping("student")
	public Student updateStudentInfo(@RequestBody StudentDTO.Update student) {
		System.out.println("학생 정보 변경 시도");
		Student stu = studentRepository.findById(student.getIdx()).get();
		stu.setNickName(student.getNickName());
		stu.setAddress(student.getAddress());
		stu.setPhone(student.getPhone());
		
		System.out.println(stu);
		return stu;
	}
}
