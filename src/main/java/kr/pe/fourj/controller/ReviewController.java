package kr.pe.fourj.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	public ResponseDTO.Create saveReview(HttpServletRequest request, @RequestBody ReviewDTO.Create dto) {
		System.out.println("-- 후기 저장 시도 --");
		
		boolean result = false;
		Long saveId = null;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			
			try {
				Student student = studentService.findOne(entity.getIdx());
				Course course = courseService.findOne(dto.getCourseIdx());
				LocalDateTime dateTime = LocalDateTime.now();
				
				if(reviewService.isNotAlreadyReview(student, course)) {
					try {
						saveId = reviewService.saveReview(new Review(student, course, dto.getContent(), dateTime, dto.getStar()));
						result = true;
					} catch (ArgumentNullException e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("동일한 강의에는 최대 한번만 리뷰를 작성할 수 있습니다.");
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}

		return new ResponseDTO.Create(saveId, result);
	}
	
	//후기 수정
	@PutMapping("/review")
	public ResponseDTO.Update updateReview(HttpServletRequest request, @RequestBody ReviewDTO.Update dto) {
		System.out.println("-- 후기 수정 시도 --");
		
		boolean result = false;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;

			try {
				Student student = studentService.findOne(entity.getIdx());
				Course course = courseService.findOne(dto.getCourseIdx());
				
				if(!reviewService.isNotAlreadyReview(student, course)) {
					reviewService.updateReview(student, course, dto);
					result = true;
				}else {
						System.out.println("본인이 작성한 리뷰만 수정할 수 있습니다. 존재하는 리뷰인지 확인하세요.");
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}
		
		return new ResponseDTO.Update(result);
	}
	
	//후기 삭제
	@DeleteMapping("/review")
	public ResponseDTO.Delete deleteReview(HttpServletRequest request, @RequestBody ReviewDTO.Delete dto) {
		System.out.println("-- 후기 삭제 시도 --");
		
		boolean result = false;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			
				try {
					Student student = studentService.findOne(entity.getIdx());
					Course course = courseService.findOne(dto.getCourseIdx());
					
					if(!reviewService.isNotAlreadyReview(student, course)) {
						reviewService.deleteReview(student, course);
						result = true;
					}else {
							System.out.println("본인이 작성한 리뷰만 삭제할 수 있습니다. 존재하는 리뷰인지 확인하세요.");
					}
				} catch (NotFoundException e) {
					e.printStackTrace();
				}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}
		
		return new ResponseDTO.Delete(result);
	}

	//???idx로 하는 검색이 쓰일까요???
	//후기 단일 검색
	@GetMapping("/review")
	public ResponseDTO.ReviewResponse findOne(@RequestBody ReviewDTO.Get dto) {
		System.out.println("-- 후기 단일 검색 시도 --");
		
		boolean result = false;
		Review review = null;
		try {
			review = reviewService.findOne(dto.getIdx());
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
		
		List<Review> reviewList = reviewService.findAll();
		
		return new ResponseDTO.ReviewListResponse(true, reviewList);
	}
	
	//특정 학생(자신)이 작성한 후기 리스트 검색
	@GetMapping("/review/studentidx")
	public ResponseDTO.ReviewListResponse findAllByStudentIdx(HttpServletRequest request) {
		System.out.println("-- 특정 학생이 작성한 후기 리스트 검색 시도 --");
		
		boolean result = false;
		List<Review> reviewList = null;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			
			try {
				Student student = studentService.findOne(entity.getIdx());
				reviewList = student.getReviewList();
				result = true;
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}
		
		return new ResponseDTO.ReviewListResponse(result, reviewList);
	}
	
	//특정 강의로 후기 리스트 검색
	@GetMapping("/review/courseidx")
	public ResponseDTO.ReviewListResponse findAllByCourseIdx(@RequestBody ReviewDTO.Get dto) {
		System.out.println("-- 특정 학생이 작성한 후기 리스트 검색 시도 --");
		
		boolean result = false;
		List<Review> reviewList = null;
		try {
			Course course = courseService.findOne(dto.getCourseIdx());
			reviewList = course.getReviewList();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.ReviewListResponse(result, reviewList);
	}
	
}
