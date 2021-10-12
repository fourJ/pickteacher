package kr.pe.fourj.controller;

import java.time.LocalDateTime;
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
	private CourseRepository courseRepository;
	
	//후기 등록
	@PostMapping("/review")
	public String saveReview(@RequestBody ReviewDTO.Create dto) {
		System.out.println("-- 후기 저장 시도 --");
		Student student = studentRepository.findById(dto.getStudentIdx()).get();
		Course course = courseRepository.findById(dto.getCourseIdx()).get();
		LocalDateTime dateTime = LocalDateTime.now();
		
		reviewRepository.save(new Review(student, course, dto.getContent(), dateTime, dto.getStar()));
		
		return "저장 성공";
	}
	
	//후기 수정
	@PutMapping("/review")
	public String updateReview(@RequestBody ReviewDTO.Update dto) {
		System.out.println("-- 후기 수정 시도 --");
		Review review = reviewRepository.findById(dto.getIdx()).get();
		LocalDateTime dateTime = LocalDateTime.now();
		
		review.setContent(dto.getContent());
		review.setDateTime(dateTime);
		review.setStar(dto.getStar());
		
		reviewRepository.save(review);
		
		return "수정 성공";
	}
	
	//후기 삭제
	@DeleteMapping("/review")
	public String deleteReview(@RequestBody ReviewDTO.Delete dto) {
		System.out.println("-- 후기 삭제 시도 --");
		
		reviewRepository.deleteById(dto.getIdx());
		
		return "삭제 성공";
	}

	//후기 단일 검색
	@GetMapping("/review")
	public Review findOne(@RequestBody ReviewDTO.Get dto) {
		System.out.println("-- 후기 단일 검색 시도 --");
		
		return reviewRepository.findById(dto.getIdx()).get();
	}
	
	//후기 리스트 전체 검색
	@GetMapping("/reviewAll")
	public List<Review> findAll() {
		System.out.println("-- 후기 리스트 전체 검색 시도 --");
		List<Review> reviewList = new ArrayList<Review>();
		
		reviewRepository.findAll().forEach(e -> reviewList.add(e));
		
		return reviewList;
	}
	
	//특정 학생이 작성한 후기 리스트 전체 검색
	@GetMapping("/findByStudentIdx")
	public List<Review> findByStudentIdx(@RequestBody ReviewDTO.Get dto) {
		System.out.println("-- 특정 학생이 작성한 후기 리스트 전체 검색 시도 --");
		Student student = studentRepository.findById(dto.getStudentIdx()).get();
		
		return student.getReviewList();
	}
	
	//특정 강의 이름으로 후기리스트 검색
	@GetMapping("/findByCourseIdx")
	public List<Review> findByCourseIdx(@RequestBody ReviewDTO.Get dto) {
		System.out.println("-- 특정 학생이 작성한 후기 리스트 전체 검색 시도 --");
		Course course = courseRepository.findById(dto.getCourseIdx()).get();
		
		return course.getReviewList();
	}
	
}
