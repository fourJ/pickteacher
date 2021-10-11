package kr.pe.fourj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Teacher;
import kr.pe.fourj.dto.TeacherDTO;
import kr.pe.fourj.dto.TeacherDTO.Create;
import kr.pe.fourj.repository.TeacherRepository;

@RestController
public class teacherController {
	
	public teacherController() {
		System.out.println("선생님 컨트롤러 생성");
	}
	
	@Autowired
	private TeacherRepository dao;
	
	@PostMapping("/teacher")
	public Create saveTeacher(TeacherDTO.Create dto) {
		dao.save(new Teacher(dto.getId(),dto.getPw(), dto.getName(), dto.getGender(), dto.getAddress(), dto.getPhone(), dto.getCareer(), dto.getMajor(), dto.getSchool(), null));
		return dto;
	}
	
	@GetMapping("/teacher")
	public List<Teacher> searchByName(String name) {
		System.out.println("이름이" + name + "인 선생님 조회 시도");
		List<Teacher> teacherList = dao.findByName(name);
		return teacherList;
	}

}