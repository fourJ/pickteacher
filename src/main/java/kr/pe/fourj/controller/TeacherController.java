package kr.pe.fourj.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Teacher;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.dto.TeacherDTO;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.service.TeacherService;

@RestController
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	
	//선생님 저장
	@PostMapping("/teacher")
	public ResponseDTO.Create saveTeacher(@RequestBody TeacherDTO.Create dto) {
		System.out.println("-- 선생님 저장 시도 --");
		
		boolean result = false;
		Long saveId = null;
		
		LocalDate date = LocalDate.now();		
		Teacher teacher = new Teacher(dto.getId(),dto.getPw(), 
									  dto.getName(), dto.getGender(), 
									  dto.getAddress(), dto.getPhone(), 
									  dto.getCareer(), dto.getMajor(), 
									  dto.getSchool(), date);
		
		try {
			saveId = teacherService.saveTeacher(new ResponseDTO.TeacherResponse(true, teacher));
			result = true;
		} catch (ArgumentNullException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.Create(saveId, result);
	}

	//선생님 수정
	@PutMapping("/teacher")
	public ResponseDTO.Update updateTeacher(@RequestBody TeacherDTO.Update dto) {
		System.out.println("-- 선생님 수정 시도 --");
		
		boolean result = false;
		try {
			teacherService.updateTeacher(dto);
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}		
		
		return new ResponseDTO.Update(result);
	}

	//선생님 삭제
	@DeleteMapping("/teacher")
	public ResponseDTO.Delete delete(@RequestBody TeacherDTO.Delete dto) {
		System.out.println("-- 선생님 삭제 시도 --");
		
		teacherService.deleteTeacher(dto);
		
		return new ResponseDTO.Delete(true);
	}
	
	//선생님 단일 검색
	@GetMapping("/teacher")
	public ResponseDTO.TeacherResponse findOne(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 선생님 단일 검색 시도 --");
		
		boolean result = false;
		Teacher teacher = null;
		try {
			teacher = teacherService.findOne(dto.getIdx()).getTeacher();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.TeacherResponse(result, teacher);
	}

	//선생님 리스트 전체 검색
	@GetMapping("/teacherall")
	public ResponseDTO.TeacherListResponse findAll() {
		System.out.println("-- 선생님 리스트 전체 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAll().getTeacherList();
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	//이름으로 조회
	@GetMapping("/teacher/name")
	public ResponseDTO.TeacherListResponse findByName(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 이름이 " + dto.getName() + " 인 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllByName(dto).getTeacherList();
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	//이름 일부로 조회
	@GetMapping("/teacher/namecontaining")
	public ResponseDTO.TeacherListResponse findByNameContaining(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 이름에 " + dto.getName() + " 들어간 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllByNameContaining(dto).getTeacherList();
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	//성별로 조회
	@GetMapping("/teacher/gender")
	public ResponseDTO.TeacherListResponse findByGender(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 성별로 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllByGender(dto).getTeacherList();
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	//출신학교명으로 조회
	@GetMapping("/teacher/school") 
	public ResponseDTO.TeacherListResponse findBySchool(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 출신학교명 " + dto.getSchool() + " 으로 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllBySchool(dto).getTeacherList();
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
		
	//출신학교명 일부로 조회
	@GetMapping("/teacher/schoolcontaining")
	public ResponseDTO.TeacherListResponse findBySchoolContaining(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 출신학교명에 " + dto.getSchool() + " 포함된 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllBySchoolContaining(dto).getTeacherList();
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	//전공명으로 조회
	@GetMapping("/teacher/major")
	public ResponseDTO.TeacherListResponse findByMajor(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 전공명 " + dto.getMajor() + " 으로 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllByMajor(dto).getTeacherList();
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	//전공명 일부로 조회
	@GetMapping("/teacher/majorcontaining")
	public ResponseDTO.TeacherListResponse findByMajorContaining(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 전공명에 " + dto.getMajor() + " 가 들어간 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllByMajorContaining(dto).getTeacherList();
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}

}