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

import kr.pe.fourj.domain.Teacher;
import kr.pe.fourj.dto.TeacherDTO;
import kr.pe.fourj.dto.TeacherDTO.Create;
import kr.pe.fourj.repository.TeacherRepository;

@RestController
public class TeacherController {
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	//선생님 저장
	@PostMapping("/teacher")
	public Create saveTeacher(@RequestBody TeacherDTO.Create dto) {
		System.out.println("-- 선생님 저장시도 --");
		LocalDate date = LocalDate.now();
		
		teacherRepository.save(new Teacher(dto.getId(),dto.getPw(), 
										   dto.getName(), dto.getGender(), 
										   dto.getAddress(), dto.getPhone(), 
										   dto.getCareer(), dto.getMajor(), 
										   dto.getSchool(), date));
		return dto;
	}

	//선생님 수정
	@PutMapping("/teacher")
	public Teacher updateTeacher(@RequestBody TeacherDTO.Update dto) {
		System.out.println("-- 선생님 수정 시도 --");
		Teacher teacher = teacherRepository.findById(dto.getIdx()).get();
		
		teacher.setAddress(dto.getAddress());
		teacher.setPhone(dto.getPhone());
		
		return teacher;
	}

	//선생님 삭제
	@DeleteMapping("/teacher")
	public String delete(@RequestBody TeacherDTO.Delete dto) {
		System.out.println("-- 선생님 삭제 시도 --");
		
		teacherRepository.deleteById(dto.getIdx());
		
		return "삭제 완료";
	}
	
	//선생님 단일 검색
	@GetMapping("/teacher")
	public Teacher findOne(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 선생님 단일 검색 시도 --");

		return teacherRepository.findById(dto.getIdx()).get();
	}

	//선생님 리스트 전체 검색
	@GetMapping("/teacherall")
	public List<Teacher> findAll() {
		System.out.println("-- 선생님 리스트 전체 검색 시도 --");
		List<Teacher> teacherList = new ArrayList<Teacher>();

		teacherRepository.findAll().forEach(e -> teacherList.add(e));
//		List<Teacher> teacherList = (ArrayList<Teacher>) teacherRepository.findAll();  //이후에 하나로 통일
//		return (ArrayList<Teacher>) teacherList;
		
		return teacherList;
	}
	
	//이름으로 조회
	@GetMapping("/teacher/name")
	public List<Teacher> findByName(@RequestBody TeacherDTO.Get dto) {
		System.out.println("이름이 " + dto + " 인 선생님 조회 시도");
		List<Teacher> teacherList = teacherRepository.findByName(dto.getName());
		
		return teacherList;
	}
	
	//이름 일부로 조회
	@GetMapping("/teacher/namecontaining")
	public List<Teacher> findByNameContaining(@RequestBody TeacherDTO.Get dto) {
		System.out.println("이름에 " + dto + " 들어간 선생님 조회");
		List<Teacher> teacherList = teacherRepository.findByNameContaining(dto.getName());
		
		return teacherList;
	}
	
	//성별로 조회
	@GetMapping("/teacher/gender")
	public List<Teacher> findByGender(@RequestBody TeacherDTO.Get dto) {
		List<Teacher> teacherList = teacherRepository.findByGender(dto.getGender());
		
		return teacherList;
	}
	
	//출신학교명으로 조회
	@GetMapping("/teacher/school") 
	public List<Teacher> findBySchool(@RequestBody TeacherDTO.Get dto) {
		System.out.println("출신학교명 " + dto + " 로 조회 시도");
		List<Teacher> teacherList = teacherRepository.findBySchool(dto.getSchool());
		
		return teacherList;
	}
		
	//출신학교명 일부로 조회
	@GetMapping("/teacher/schoolcontaining")
	public List<Teacher> findBySchoolContaining(@RequestBody TeacherDTO.Get dto) {
		System.out.println("출신학교명에" + dto + " 포함된 학교 조회 시도");
		List<Teacher> teacherList = teacherRepository.findBySchoolContaining(dto.getSchool());
		
		return teacherList;
	}
	
	//전공명으로 조회
	@GetMapping("/teacher/major")
	public List<Teacher> findByMajor(@RequestBody TeacherDTO.Get dto) {
		System.out.println("전공명 " + dto + " 으로 조회 시도");
		List<Teacher> teacherList = teacherRepository.findByMajor(dto.getMajor());
		
		return teacherList;
	}
	
	//전공명 일부로 조회
	@GetMapping("/teacher/majorcontaining")
	public List<Teacher> findByMajorContaining(@RequestBody TeacherDTO.Get dto) {
		System.out.println("전공명에 " + dto + " 가 들어간 전공 조회 시도");
		List<Teacher> teacherList = teacherRepository.findByMajorContaining(dto.getMajor());
		
		return teacherList;
	}

}