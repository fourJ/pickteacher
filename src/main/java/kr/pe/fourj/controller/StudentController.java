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

import kr.pe.fourj.domain.Catalog;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.dto.StudentDTO;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.service.CatalogService;
import kr.pe.fourj.service.StudentService;

@RestController
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	@Autowired
	private CatalogService catalogService;
	
	//학생 저장
	@PostMapping("/student")
	public ResponseDTO.Create saveStudent(@RequestBody StudentDTO.Create dto) {
		System.out.println("-- 학생 저장시도 --");
		
		boolean result = false;
		Long saveId = null;

		Calendar current = new GregorianCalendar();
		Calendar birth = new GregorianCalendar();
		birth.setTime(dto.getBirth());
		current.setTime(new Date());
		int age = current.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + 1;
		
		Student student = new Student(dto.getId(), dto.getPw(), 
									  dto.getName(), dto.getBirth(), age, 
									  dto.getNickName(), dto.getGender(), 
									  dto.getAddress(), dto.getPhone());
		try {
			saveId = studentService.saveStudent(new ResponseDTO.StudentResponse(true, student));
			result = true;
		} catch (ArgumentNullException e) {
			e.printStackTrace();
		}

		return new ResponseDTO.Create(saveId, result);
	}

	//학생 수정
	@PutMapping("/student")
	public ResponseDTO.Update updateStudent(@RequestBody StudentDTO.Update dto) {
		System.out.println("-- 학생 수정 시도 --");
		
		boolean result = false;
		try {
			studentService.updateStudent(dto);
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.Update(result);
	}

	//학생 삭제
	@DeleteMapping("/student")
	public ResponseDTO.Delete deleteStudent(@RequestBody StudentDTO.Delete dto) {
		System.out.println("-- 학생 삭제 시도 --");
		
		studentService.deleteStudent(dto);

		return new ResponseDTO.Delete(true);
	}

	//학생 단일 검색
	@GetMapping("/student")
	public ResponseDTO.StudentResponse findOne(@RequestBody StudentDTO.Get dto){
		System.out.println("-- 학생 단일 검색 시도 --");
		
		boolean result = false;
		Student student = null;
		try {
			student = studentService.findOne(dto.getIdx()).getStudent();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.StudentResponse(result, student);
	}

	//학생 리스트 전체 검색
	@GetMapping("/studentall")
	public ResponseDTO.StudentListResponse findAll(){
		System.out.println("-- 학생 리스트 전체 검색 시도 --");
		
		List<Student> studentList = studentService.findAll().getStudentList();
		
		return new ResponseDTO.StudentListResponse(true, studentList);
	}
	
	//특정 강의를 수강하는 학생 리스트 검색
	@GetMapping("/student/courseIdx")
	public ResponseDTO.StudentListResponse findBycourseIdx(@RequestBody StudentDTO.Get dto) {
		System.out.println("-- 특정 강의를 수강하는 학생 리스트 검색 시도 --");
		
		List<Student> studentList = new ArrayList<Student>();
		List<Catalog> catalogList = catalogService.findAllByCourseIdx(dto.getCourseIdx()).getCatalogList();
		catalogList.forEach(e -> studentList.add(e.getStudentIdx()));
		
		return new ResponseDTO.StudentListResponse(true, studentList);
	}
	
}
