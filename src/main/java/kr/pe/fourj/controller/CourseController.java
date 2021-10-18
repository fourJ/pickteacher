package kr.pe.fourj.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Catalog;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.domain.Teacher;
import kr.pe.fourj.dto.CourseDTO;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.service.CourseService;
import kr.pe.fourj.service.ReviewService;
import kr.pe.fourj.service.StudentService;
import kr.pe.fourj.service.TeacherService;

@RestController
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ReviewService reviewService;
	
	//강의 저장
	@PostMapping("/course")
	public ResponseDTO.Create saveCourse(@RequestBody CourseDTO.Create dto) {
		System.out.println("-- 강의 저장 시도 --");
		
		boolean result = false;
		Long saveId = null;
			String status = courseService.calculateStatus(dto.getOpenDate(), dto.getCloseDate());
			try {
				Teacher teacher = teacherService.findOne(dto.getTeacherIdx());

				try {
					saveId = courseService.saveCourse(new Course(teacher, 
							dto.getTitle(), dto.getSubject(), 
							dto.getSchedule(), dto.getType(), 
							dto.getOpenDate(), dto.getCloseDate(), 
							status, dto.getHeadCount(), 
							dto.getTuition(), dto.getTarget()));
					result = true;
				} catch (ArgumentNullException e1) {
					e1.printStackTrace();
				}
			} catch (NotFoundException e2) {
				e2.printStackTrace();
			}
		
		return new ResponseDTO.Create(saveId, result);
	}

	//강의 삭제
	@DeleteMapping("/course")
	public ResponseDTO.Delete deleteCourse(CourseDTO.Delete dto) {
		System.out.println("-- 강의 삭제 시도 --");

		boolean result = false;			
		try {
			courseService.deleteCourse(dto);
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseDTO.Delete(result);
	}
	
	//강의 단일 검색
	@GetMapping("/course")
	public ResponseDTO.CourseResponse findOne(CourseDTO.Get dto) {
		System.out.println("-- 강의 단일 검색 시도 --");
		
		boolean result = false;
		Course course = null;
		try {
			course = courseService.findOne(dto.getIdx());
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.CourseResponse(result, course);
	}

	//강의 리스트 전체 검색
	@GetMapping("/courseall")
	public ResponseDTO.CourseListResponse findAll() {
		System.out.println("-- 강의 리스트 전체 검색 시도 --");
		
		List<Course> courseList = courseService.findAll();
		
		return new ResponseDTO.CourseListResponse(true, courseList);
	}
	
	//특정 강의 제목 일부 포함된 강의 리스트 검색
	@GetMapping("/course/titlecontaining")
	public ResponseDTO.CourseListResponse findAllByTitleContaining(CourseDTO.Get dto) {
		System.out.println("-- 강의 제목이 " + dto.getTitle() + "인 강의 리스트 검색 시도 --");
		
		List<Course> courseList = courseService.findAllByTitleContaining(dto);

		return new ResponseDTO.CourseListResponse(true, courseList);
	}

	//특정 선생님의 강의 리스트 검색
	@GetMapping("/course/teacheridx")
	public ResponseDTO.CourseListResponse findAllByTeacherIdx(CourseDTO.Get dto) {
		System.out.println("-- 나의 강의 리스트 검색 시도 --");
		
		boolean result = false;
		List<Course> courseList = null;
		try {
			Teacher teacher = teacherService.findOne(dto.getTeacherIdx());
			courseList = teacher.getCourseList();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseDTO.CourseListResponse(result, courseList);
	}
	
	//과목으로 강의 리스트 검색
	@GetMapping("/course/subject")
	public ResponseDTO.CourseListResponse findAllBySubject(CourseDTO.Get dto) {
		System.out.println("-- 특정 과목 강의 리스트 검색 시도 --");

		List<Course> courseList = courseService.findAllBySubject(dto);

		return new ResponseDTO.CourseListResponse(true, courseList);
	}
	
	//타겟(대상학년)으로 강의 리스트 검색
	@GetMapping("/course/target")
	public ResponseDTO.CourseListResponse findAllByTarget(CourseDTO.Get dto) {
		System.out.println("-- 특정 타겟으로 강의 리스트 검색 시도 --");

		List<Course> courseList = courseService.findAllByTarget(dto);

		return new ResponseDTO.CourseListResponse(true, courseList);
	}
	
	//스케줄(요일)로 강의 리스트 검색
	@GetMapping("/course/schedule")
	public ResponseDTO.CourseListResponse findAllBySchedule(CourseDTO.Get dto) {
		System.out.println("-- 특정 타겟으로 강의 리스트 검색 시도 --");

		List<Course> courseList = courseService.findAllBySchedule(dto);

		return new ResponseDTO.CourseListResponse(true, courseList);
	}
	
	//타입(진행방식)으로 강의 리스트 검색
	@GetMapping("/course/type")
	public ResponseDTO.CourseListResponse findAllByType(CourseDTO.Get dto) {
		System.out.println("-- 특정 타겟으로 강의 리스트 검색 시도 --");

		List<Course> courseList = courseService.findAllByType(dto);

		return new ResponseDTO.CourseListResponse(true, courseList);
	}
	
	//가격으로 강의 리스트 검색(입력된 금액 이하 강의 찾기)
	@GetMapping("/course/tuition")
	public ResponseDTO.CourseListResponse findAllByTuition(CourseDTO.Get dto) {
		System.out.println("-- 특정 타겟으로 강의 리스트 검색 시도 --");

		List<Course> courseList = courseService.findAllByTuition(dto);

		return new ResponseDTO.CourseListResponse(true, courseList);
	}
	
	//특정 학생이 수강했지만 리뷰는 남기지 않은 강의 리스트 검색
	@GetMapping("/course/noreview")
	public ResponseDTO.CourseListResponse findAllNoReview(CourseDTO.Get dto) {
		System.out.println("-- 특정 학생이 수강했지만 리뷰는 남기지 않은 강의 리스트 검색 시도 --");
		
		boolean result = false;
		List<Course> courseList = new ArrayList<Course>();
		try {
			Student student = studentService.findOne(dto.getStudentIdx());
			List<Catalog> studentCatalogList = student.getCatalogList();
			
			for(int i = 0; i < studentCatalogList.size(); i++) {
				if(reviewService.isNotAlreadyReview(student, studentCatalogList.get(i).getCourseIdx())) {
					courseList.add(studentCatalogList.get(i).getCourseIdx());
				}
			}
			result = true;
			
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.CourseListResponse(result, courseList);
	}
	
}
