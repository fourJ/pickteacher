package kr.pe.fourj.controller;

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
	
	//강의 저장
	@PostMapping("/course")
	public String saveCourse(@RequestBody CourseDTO.Create dto) {
		System.out.println("-- 강의 저장 시도 --");
		Teacher teacher = teacherRepository.findById(dto.getTeacherIdx()).get();
		
		courseRepository.save(new Course(teacher, 
										 dto.getTitle(), dto.getSubject(), 
										 dto.getSchedule(), dto.getType(), 
										 dto.getOpenDate(), dto.getCloseDate(),
										 dto.getStatus(), dto.getHeadCount(), 
										 dto.getTuition(), dto.getTarget()));

		return "강의가 열렸습니다!";
	}

	//강의 수정
	@PutMapping("/course")
	public String updateCourse(@RequestBody CourseDTO.Update dto) {
		System.out.println("-- 강의 수정 시도 --");
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

	//강의 삭제
	@DeleteMapping("/course")
	public String deleteCourse(@RequestBody CourseDTO.Delete dto) {
		System.out.println("-- 강의 삭제 시도 --");
		
		courseRepository.deleteById(dto.getIdx());
		
		return "강의를 삭제했습니다!";
	}
	
	//강의 단일 검색
	@GetMapping("/course")
	public Course findOne(@RequestBody CourseDTO.Get dto) {
		System.out.println("-- 강의 단일 검색 시도 --");
		
		return courseRepository.findById(dto.getIdx()).get();
	}

	//강의 리스트 전체 검색
	@GetMapping("/courseall")
	public List<Course> findAll() {
		System.out.println("-- 강의 리스트 전체 검색 시도 --");
		List<Course> courseList = new ArrayList<Course>();
		
		courseRepository.findAll().forEach(e -> courseList.add(e));
		
		return courseList;
	}
	
	//특정 강의 제목으로 강의 리스트 검색
	@GetMapping("/course/title")
	public List<Course> findCourseByTitle(@RequestBody CourseDTO.Get dto) {
		System.out.println("-- 특정 강의 제목으로 강의 리스트 검색 시도 --");
		List<Course> courseList = new ArrayList<Course>();
		
		courseRepository.findCourseListByTitleContaining(dto.getTitle()).forEach(e -> courseList.add(e));

		return courseList;
	}

	//특정 선생님의 강의 리스트 검색
	@GetMapping("/course/teacherIdx")
	public List<Course> findByTeacherIdx(@RequestBody CourseDTO.Get dto) {
		System.out.println("선생님 idx로 해당 선생님의 강의 리스트 조회 시도");
		Teacher teacher = teacherRepository.findById(dto.getTeacherIdx()).get();
		
		return teacher.getCourseList();
	}

}
