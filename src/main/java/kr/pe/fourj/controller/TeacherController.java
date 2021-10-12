package kr.pe.fourj.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Teacher;
import kr.pe.fourj.dto.TeacherDTO;
import kr.pe.fourj.dto.TeacherDTO.Create;
import kr.pe.fourj.repository.TeacherRepository;
import lombok.NonNull;

@RestController
public class TeacherController {
	
	public TeacherController() {
		System.out.println("선생님 컨트롤러 생성");
	}
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	//저장
	@PostMapping("/teacher")
	public Create save(@RequestBody TeacherDTO.Create dto) {
		System.out.println("선생님 정보 저장 시도");
		@NonNull LocalDate date = LocalDate.now();
		teacherRepo.save(new Teacher(dto.getId(),dto.getPw(), dto.getName(), dto.getGender(), dto.getAddress(), dto.getPhone(), dto.getCareer(), dto.getMajor(), dto.getSchool(), date));
		return dto;
	}
	
	//모든 선생님 정보 조회
	@GetMapping("/teacherall")
	public List<Teacher> getAllTeachers() {
		System.out.println("모든 선생님 정보 조회 시도");
		List<Teacher> teacherList = (ArrayList<Teacher>) teacherRepo.findAll();
		return (ArrayList<Teacher>) teacherList;
	}
	
	//이름으로 조회
	@GetMapping("/teacher-name1")
	public List<Teacher> searchByName1(@RequestBody TeacherDTO.Get dto) {
		System.out.println("이름이 " + dto + " 인 선생님 조회 시도");
		List<Teacher> teacherList = teacherRepo.findByName(dto.getName());
		return teacherList;
	}
	
	//이름 일부로 조회
	@GetMapping("/teacher-name2")
	public List<Teacher> searchByName2(@RequestBody TeacherDTO.Get dto) {
		System.out.println("이름에 " + dto + " 들어간 선생님 조회");
		List<Teacher> teacherList = teacherRepo.findByNameContaining(dto.getName());
		return teacherList;
	}
	
	//성별로 조회
	@GetMapping("/teacher-gender")
	public List<Teacher> searchByGender(@RequestBody TeacherDTO.Get dto) {
		List<Teacher> teacherList = teacherRepo.findByGender(dto.getGender());
		return teacherList;
	}
	
	//출신학교명으로 조회
	@GetMapping("/teacher-school1") 
	public List<Teacher> searchBySchool1(@RequestBody TeacherDTO.Get dto) {
		System.out.println("출신학교명 " + dto + " 로 조회 시도");
		List<Teacher> teacherList = teacherRepo.findBySchool(dto.getSchool());
		return teacherList;
	}
		
	//출신학교명 일부로 조회
	@GetMapping("/teacher-school2")
	public List<Teacher> searchBySchool2(@RequestBody TeacherDTO.Get dto) {
		System.out.println("출신학교명에" + dto + " 포함된 학교 조회 시도");
		List<Teacher> teacherList = teacherRepo.findBySchoolContaining(dto.getSchool());
		return teacherList;
	}
	
	//전공명으로 조회
	@GetMapping("/teacher-major1")
	public List<Teacher> searchByMajor1(@RequestBody TeacherDTO.Get dto) {
		System.out.println("전공명 " + dto + " 으로 조회 시도");
		List<Teacher> teacherList = teacherRepo.findByMajor(dto.getMajor());
		return teacherList;
	}
	
	//전공명 일부로 조회
	@GetMapping("/teacher-major2")
	public List<Teacher> searchByMajor2(@RequestBody TeacherDTO.Get dto) {
		System.out.println("전공명에 " + dto + " 가 들어간 전공 조회 시도");
		List<Teacher> teacherList = teacherRepo.findByMajorContaining(dto.getMajor());
		return teacherList;
	}
	
	//선생님 idx로 강의 리스트 조회
	@GetMapping("/teacher-courselist")
	public List<Course> getCourseList(@RequestBody TeacherDTO.Get dto) {
		System.out.println("선생님 idx로 해당 선생님의 강의 리스트 조회 시도");
		Teacher teacher = teacherRepo.findById(dto.getIdx()).get();
		return teacher.getCourseList();
	}
	
	//수정
	@PutMapping("/teacher")
	public Teacher update(@RequestBody TeacherDTO.Update dto) {
		System.out.println("선생님 정보 수정 시도");
		Teacher teacher = teacherRepo.findById(dto.getIdx()).get();
		teacher.setAddress(dto.getAddress());
		teacher.setPhone(dto.getPhone());
		return teacher;
	}
	
	//삭제
	@DeleteMapping("/teacher")
	public String delete(@RequestBody TeacherDTO.Delete dto) {
		System.out.println("선생님 정보 삭제 시도");
		teacherRepo.deleteById(dto.getIdx());
		return "삭제 완료";
	}
	

}