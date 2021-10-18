package kr.pe.fourj.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Catalog;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.dto.StudentDTO;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.StudentRepository;
import kr.pe.fourj.service.CatalogService;
import kr.pe.fourj.service.CourseService;
import kr.pe.fourj.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private CourseService courseService;
	@Autowired
	StudentRepository studentRepository;

	//학생 저장
	@PostMapping("/student")
	public ResponseDTO.Create saveStudent(StudentDTO.Create dto) throws NotFoundException {
		System.out.println("-- 학생 저장시도 --");

		boolean result = false;
		Long saveId = null;
		
		LocalDate now = LocalDate.now();
		LocalDate birth = dto.getBirth();
		
		Period period = Period.between(birth, now);
		int age = period.getYears() + 1;


		if (studentService.findStudentById(dto.getId()) == null) {
			try {
				saveId = studentService.saveStudent(new Student(dto.getId(), dto.getPw(), dto.getName(), dto.getBirth(),
						age, dto.getNickName(), dto.getGender(), dto.getAddress(), dto.getPhone()));
				result = true;					
			} catch (ArgumentNullException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("이미 존재하는 회원id입니다.");
		}

		return new ResponseDTO.Create(saveId, result);
	}
	
	//학생 로그인
	@RequestMapping(value="/student/login", method=RequestMethod.POST) 
	public ResponseDTO.Login login(@RequestBody StudentDTO.Login dto) {

		boolean result = false;
		Student student = studentService.findStudentById(dto.getId());
		if(student != null) {
			if(student.getPw().equals(dto.getPw())) {
				result = true;
			}else {
				System.out.println("로그인 실패! : 패스워드를 다시 확인해주세요. 중복 로그인은 불가합니다.");
			}
		}else {
			System.out.println("로그인 실패! : 등록되지 않은 회원입니다.");
		}

		return new ResponseDTO.Login(result, student.getIdx());
	}

	//학생 수정
	@PutMapping("/student")
	public ResponseDTO.Update updateStudent(StudentDTO.Update dto) {
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
	public ResponseDTO.Delete deleteStudent(StudentDTO.Delete dto) {
		System.out.println("-- 학생 삭제 시도 --");

		boolean result = false;
		try {
			studentService.deleteStudent(dto);
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}		

		return new ResponseDTO.Delete(result);
	}

	//특정 학생(자신) 단일 검색
	@GetMapping("/student")
	public ResponseDTO.StudentResponse findOne(StudentDTO.Get dto) {
		System.out.println("-- 학생 단일 검색 시도 --");
		boolean result = false;
		Student student = null;
		try {
			student = studentService.findOne(dto.getIdx());
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseDTO.StudentResponse(result, student);
	}

	//특정 강의를 수강하는 학생 리스트 검색
	@GetMapping("/student/courseIdx")
	public ResponseDTO.StudentListResponse findAllBycourseIdx(StudentDTO.Get dto) {
		System.out.println("-- 특정 강의를 수강하는 학생 리스트 검색 시도 --");

		boolean result = false;
		List<Student> studentList = new ArrayList<Student>();
		try {
			Course course = courseService.findOne(dto.getCourseIdx());
			List<Catalog> catalogList = catalogService.findAllByCourseIdx(course);
			catalogList.forEach(e -> studentList.add(e.getStudentIdx()));
			result = true;
		} catch (NotFoundException e1) {
			e1.printStackTrace();
		}

		return new ResponseDTO.StudentListResponse(result, studentList);
	}

}
