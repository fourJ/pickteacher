package kr.pe.fourj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Review;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.ReviewDTO;
import kr.pe.fourj.repository.CourseRepository;
import kr.pe.fourj.repository.ReviewRepository;
import kr.pe.fourj.repository.StudentRepository;

@RestController
public class ReviewController {
	
	public ReviewController() {}
	
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository CourseRepository;
	
	//후기등록
	@PostMapping("/review")
	public String saveReview(@RequestParam ReviewDTO.Create dto) {
		System.out.println("-- 리뷰 저장 시도 --");
		Student student = studentRepository.findById(dto.getStudentIdx()).get();
		Course course = CourseRepository.findById(dto.getCourseIdx()).get();
		
		reviewRepository.save(new Review(student, course, dto.getContent(), dto.getDateTime(), dto.getStar()));
		
		return "저장 성공";
	}
	
	//후기수정
	
	//후기삭제
	
	//후기전체리스트 검색
	
	//특정 학생id로 후기리스트 검색
	
	//특정 강의 이름으로 후기리스트 검색
	
	//
}
