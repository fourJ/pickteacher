package kr.pe.fourj.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Teacher;
import kr.pe.fourj.dto.CourseDTO;
import kr.pe.fourj.repository.CourseRepository;
import kr.pe.fourj.repository.TeacherRepository;

@RestController
public class CourseController {
	
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private TeacherRepository teacherRepository;
	
	// 강의 생성
	@PostMapping("/coursesave")
	public String createCourse(@RequestBody CourseDTO.Create dto) {
		System.out.println("--- 강의 등록 시도 ---");
		Teacher teacher = teacherRepository.findById(dto.getTeacherIdx()).get();
		courseRepository.save(new Course(teacher, 
										 dto.getTitle(), dto.getSubject(), 
										 dto.getSchedule(), dto.getType(), 
										 dto.getOpenDate(), dto.getCloseDate(),
										 dto.getStatus(), dto.getHeadCount(), 
										 dto.getTuition(), dto.getTarget()));
		return "강의가 열렸습니다!";
	}
	
	// 강의 전체 검색
	@GetMapping("/courseall")
	public List<Course> findAllCourse() {
		System.out.println("--- 강의 전체 검색 시도 ---");
		List<Course> courseList = new ArrayList<Course>();
		courseRepository.findAll().forEach(e -> courseList.add(e));
		return courseList;
	}
	
	// 특정 강의 제목으로 검색
	@GetMapping("/coursetitle")
	public List<Course> findCourseByTitle(@RequestBody String title) {
		System.out.println(title + "--- 해당 강의 검색 시도 ---");
		List<Course> courseList = new ArrayList<Course>();
		courseRepository.findCourseListByTitleContaining(title).forEach(e -> courseList.add(e));
		return courseList;
	}
	
	// 강의 수정
	@PutMapping("/course")
	public String updateCourse(@RequestBody CourseDTO.Update dto) {
		System.out.println("--- 수업시간 수정 ---");
		Course course = courseRepository.findById(dto.getIdx()).get();
		course.setSchedule(dto.getSchedule());
		course.setOpenDate(dto.getOpenDate());
		course.setCloseDate(dto.getCloseDate());
		course.setType(dto.getType());
		course.setStatus(dto.getStatus());
		course.setTuition(dto.getTuition());
		course.setTarget(dto.getTarget());
		courseRepository.save(course);
		return "수정했습니다";
	}
	
	// 강의 삭제 - id로 찾아서 삭제
	@DeleteMapping("/coursedeleteidx")
	public String deleteCourseIdx(@RequestBody CourseDTO.Delete dto) {
		System.out.println("--- 강의 삭제 시도 ---");
		courseRepository.deleteById(dto.getIdx());
		return "강의를 삭제했습니다!";
	}
	
}
