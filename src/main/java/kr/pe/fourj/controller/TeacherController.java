package kr.pe.fourj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Teacher;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.dto.TeacherDTO;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.service.TeacherService;

@RestController
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;

	//선생님 로그인
	@RequestMapping("/teacher/login")
	public ResponseDTO.Login login(TeacherDTO.Login dto) {

		boolean result = false;
		Teacher teacher = teacherService.findTeacherById(dto.getId());
		if(teacher != null) {
			if(teacher.getPw().equals(dto.getPw())) {
				result = true;
			}else {
				System.out.println("로그인 실패! : 패스워드를 다시 확인해주세요. 중복 로그인은 불가합니다.");
			}
		}else {
			System.out.println("로그인 실패! : 등록되지 않은 회원입니다.");
		}

		return new ResponseDTO.Login(result, teacher.getIdx());
	}

	//선생님 수정
	@PutMapping("/teacher")
	public ResponseDTO.Update updateTeacher(TeacherDTO.Update dto) {
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
	public ResponseDTO.Delete delete(TeacherDTO.Delete dto) {
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
	public ResponseDTO.TeacherResponse findOne(TeacherDTO.Get dto) {
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
	
	//이름 일부로 검색
	@GetMapping("/teacher/namecontaining")
	public ResponseDTO.TeacherListResponse findAllByNameContaining(TeacherDTO.Get dto) {
		System.out.println("-- 이름에 " + dto.getName() + " 들어간 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllByNameContaining(dto);
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	//성별로 검색
	@GetMapping("/teacher/gender")
	public ResponseDTO.TeacherListResponse findAllByGender(TeacherDTO.Get dto) {
		System.out.println("-- 성별로 선생님 검색 시도 --");
		
		List<Teacher> teacherList = teacherService.findAllByGender(dto);
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}

	//경력으로 검색
	@GetMapping("/teacher/career")
	public ResponseDTO.TeacherListResponse findAllByCareer(TeacherDTO.Get dto) {
		System.out.println("-- 경력으로 선생님 검색 시도 --");

		List<Teacher> teacherList = teacherService.findAllByCareer(dto);

		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}

}