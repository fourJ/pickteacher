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
		
		try {
			saveId = teacherService.saveTeacher(new Teacher(dto.getId(),dto.getPw(), 
															dto.getName(), dto.getGender(), 
															dto.getAddress(), dto.getPhone(), 
															dto.getCareer(), dto.getMajor(), 
															dto.getSchool(), date));
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
		
		boolean result = false;
		try {
			teacherService.deleteTeacher(dto);
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.Delete(result);
	}
	
	//선생님 단일 검색
	@GetMapping("/teacher")
	public ResponseDTO.TeacherResponse findOne(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 선생님 단일 검색 시도 --");
		
		boolean result = false;
		Teacher teacher = null;
		try {
			teacher = teacherService.findOne(dto.getIdx());
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
		
		List<Teacher> teacherList = teacherService.findAll();
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	//이름으로 검색
	@GetMapping("/teacher/name")
	public ResponseDTO.TeacherListResponse findAllByName(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 이름이 " + dto.getName() + " 인 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllByName(dto);
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	//이름 일부로 검색
	@GetMapping("/teacher/namecontaining")
	public ResponseDTO.TeacherListResponse findAllByNameContaining(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 이름에 " + dto.getName() + " 들어간 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllByNameContaining(dto);
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	//성별로 검색
	@GetMapping("/teacher/gender")
	public ResponseDTO.TeacherListResponse findAllByGender(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 성별로 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllByGender(dto);
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	//출신학교명으로 검색
	@GetMapping("/teacher/school") 
	public ResponseDTO.TeacherListResponse findAllBySchool(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 출신학교명 " + dto.getSchool() + " 으로 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllBySchool(dto);
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
		
	//출신학교명 일부로 검색
	@GetMapping("/teacher/schoolcontaining")
	public ResponseDTO.TeacherListResponse findAllBySchoolContaining(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 출신학교명에 " + dto.getSchool() + " 포함된 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllBySchoolContaining(dto);
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	//전공명으로 조회
	@GetMapping("/teacher/major")
	public ResponseDTO.TeacherListResponse findAllByMajor(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 전공명 " + dto.getMajor() + " 으로 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllByMajor(dto);
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	//전공명 일부로 조회
	@GetMapping("/teacher/majorcontaining")
	public ResponseDTO.TeacherListResponse findAllByMajorContaining(@RequestBody TeacherDTO.Get dto) {
		System.out.println("-- 전공명에 " + dto.getMajor() + " 가 들어간 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllByMajorContaining(dto);
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}

}