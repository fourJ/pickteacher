package kr.pe.fourj.controller;

import java.time.LocalDateTime;
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
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.dto.ReviewDTO;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.service.CourseService;
import kr.pe.fourj.service.ReviewService;
import kr.pe.fourj.service.StudentService;

@RestController
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private CourseService courseService;
	
	//후기 저장
	@PostMapping("/review")
	public ResponseDTO.Create saveReview(@RequestBody ReviewDTO.Create dto) {
		System.out.println("-- 후기 저장 시도 --");
		
		boolean result = false;
		Long saveId = null;
		try {
			Student student = studentService.findOne(dto.getStudentIdx()).getStudent();
			Course course = courseService.findOne(dto.getCourseIdx()).getCourse();
			LocalDateTime dateTime = LocalDateTime.now();

			Review review = new Review(student, course, dto.getContent(), dateTime, dto.getStar());
			try {
				saveId = reviewService.saveReview(new ResponseDTO.ReviewResponse(true, review));
				result = true;
			} catch (ArgumentNullException e) {
				e.printStackTrace();
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseDTO.Create(saveId, result);
	}
	
	//후기 수정
	@PutMapping("/review")
	public ResponseDTO.Update updateReview(@RequestBody ReviewDTO.Update dto) {
		System.out.println("-- 후기 수정 시도 --");
		
		boolean result = false;
		try {
			reviewService.updateReview(dto);
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.Update(result);
	}
	
	//후기 삭제
	@DeleteMapping("/review")
	public ResponseDTO.Delete deleteReview(@RequestBody ReviewDTO.Delete dto) {
		System.out.println("-- 후기 삭제 시도 --");
		
		reviewService.deleteReview(dto);
		
		return new ResponseDTO.Delete(true);
	}

	//후기 단일 검색
	@GetMapping("/review")
	public ResponseDTO.ReviewResponse findOne(@RequestBody ReviewDTO.Get dto) {
		System.out.println("-- 후기 단일 검색 시도 --");
		
		boolean result = false;
		Review review = null;
		try {
			review = reviewService.findOne(dto.getIdx()).getReview();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.ReviewResponse(result, review);
	}
	
	//후기 리스트 전체 검색
	@GetMapping("/reviewall")
	public ResponseDTO.ReviewListResponse findAll() {
		System.out.println("-- 후기 리스트 전체 검색 시도 --");
		
		List<Review> reviewList = (List<Review>) reviewService.findAll().getReviewList();
		
		return new ResponseDTO.ReviewListResponse(true, reviewList);
	}
	
	//특정 학생이 작성한 후기 리스트 검색
	@GetMapping("/review/studentidx")
	public ResponseDTO.ReviewListResponse findByStudentIdx(@RequestBody ReviewDTO.Get dto) {
		System.out.println("-- 특정 학생이 작성한 후기 리스트 검색 시도 --");
		
		boolean result = false;
		List<Review> reviewList = null;
		try {
			Student student = studentService.findOne(dto.getStudentIdx()).getStudent();
			reviewList = student.getReviewList();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.ReviewListResponse(result, reviewList);
	}
	
	//특정 강의 이름으로 후기 리스트 검색
	@GetMapping("/review/courseidx")
	public ResponseDTO.ReviewListResponse findByCourseIdx(@RequestBody ReviewDTO.Get dto) {
		System.out.println("-- 특정 학생이 작성한 후기 리스트 검색 시도 --");
		
		boolean result = false;
		List<Review> reviewList = null;
		try {
			Course course = courseService.findOne(dto.getCourseIdx()).getCourse();
			reviewList = course.getReviewList();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.ReviewListResponse(result, reviewList);
	}
	
}
