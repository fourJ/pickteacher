package kr.pe.fourj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import kr.pe.fourj.repository.CourseRepository;

public class CourseController {
	
	@Autowired
	private CourseRepository dao;
	
//	@GetMapping("course")
//	public String createCourse() {
//		System.out.println("");
//		dao.save(new Course())
//		return corse + "강의가 열렸습니다!";
//	}

}
