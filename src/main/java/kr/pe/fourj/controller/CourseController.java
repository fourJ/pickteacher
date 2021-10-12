package kr.pe.fourj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Teacher;
import kr.pe.fourj.dto.CourseDTO;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.service.CourseService;
import kr.pe.fourj.service.TeacherService;

@RestController
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	@Autowired
	private TeacherService teacherService;
	
	//강의 저장
	@PostMapping("/course")
	public ResponseDTO.Create saveCourse(@RequestBody CourseDTO.Create dto) {
		System.out.println("-- 강의 저장 시도 --");
		
		boolean result = false;
		Long saveId = null;
		try {
			Teacher teacher = teacherService.findOne(dto.getTeacherIdx()).getTeacher();
			
			Course course = new Course(teacher, 
									   dto.getTitle(), dto.getSubject(), 
									   dto.getSchedule(), dto.getType(), 
									   dto.getOpenDate(), dto.getCloseDate(), 
									   dto.getStatus(), dto.getHeadCount(), 
									   dto.getTuition(), dto.getTarget());
			try {
				saveId = courseService.saveCourse(new ResponseDTO.CourseResponse(true, course));
				result = true;
			} catch (ArgumentNullException e) {
				e.printStackTrace();
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.Create(saveId, result);
	}

	//강의 수정
	@PutMapping("/course")
	public ResponseDTO.Update updateCourse(@RequestBody CourseDTO.Update dto) {
		System.out.println("-- 강의 수정 시도 --");
		
		boolean result = false;
		try {
			courseService.updateCourse(dto);
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseDTO.Update(result);
	}

	//강의 삭제
	@DeleteMapping("/course")
	public ResponseDTO.Delete deleteCourse(@RequestBody CourseDTO.Delete dto) {
		System.out.println("-- 강의 삭제 시도 --");
		
		courseService.deleteCourse(dto);
		
		return new ResponseDTO.Delete(true);
	}
	
	//강의 단일 검색
	@GetMapping("/course")
	public ResponseDTO.CourseResponse findOne(@RequestBody CourseDTO.Get dto) {
		System.out.println("-- 강의 단일 검색 시도 --");
		
		boolean result = false;
		Course course = null;
		try {
			course = courseService.findOne(dto.getIdx()).getCourse();
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
		
		List<Course> courseList = courseService.findAll().getCourseList();
		
		return new ResponseDTO.CourseListResponse(true, courseList);
	}
	
	//특정 강의 제목으로 강의 리스트 검색
	@GetMapping("/course/title")
	public ResponseDTO.CourseListResponse findCourseByTitle(@RequestBody CourseDTO.Get dto) {
		System.out.println("-- 특정 강의 제목으로 강의 리스트 검색 시도 --");
		
		List<Course> courseList = courseService.findAllByTitle(dto.getTitle()).getCourseList();

		return new ResponseDTO.CourseListResponse(true, courseList);
	}

	//특정 선생님의 강의 리스트 검색
	@GetMapping("/course/teacherIdx")
	public ResponseDTO.CourseListResponse findByTeacherIdx(@RequestBody CourseDTO.Get dto) {
		System.out.println("-- 특정 선생님의 강의 리스트 검색 시도 --");
		
		boolean result = false;
		List<Course> courseList = null;
		try {
			Teacher teacher = teacherService.findOne(dto.getTeacherIdx()).getTeacher();
			courseList = teacher.getCourseList();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.CourseListResponse(result, courseList);
	}

}
