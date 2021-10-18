package kr.pe.fourj.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.dto.CourseDTO;
import kr.pe.fourj.exception.Exception;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	
	public Long saveCourse(Course course) throws ArgumentNullException {
		Course save = null;
		
		if(course == null) {
			throw new Exception.ArgumentNullException("Course can't be null");
		}
		save = courseRepository.save(course);
		
		return save.getIdx();
	}
	
	public void updateCourse(Long courseIdx, CourseDTO.Update dto) throws NotFoundException {
		Course course = findOne(courseIdx);
		
		course.setTitle(dto.getTitle());
		course.setSchedule(dto.getSchedule());
		course.setOpenDate(dto.getOpenDate());
		course.setCloseDate(dto.getCloseDate());
		course.setStatus(calculateStatus(dto.getOpenDate(), dto.getCloseDate()));
		course.setHeadCount(dto.getHeadCount());
		course.setType(dto.getType());
		course.setTuition(dto.getTuition());
		course.setTarget(dto.getTarget());
		
		courseRepository.save(course);
	}
	
	public String calculateStatus(LocalDate openDate, LocalDate closeDate) {
		String status;
		
		if(LocalDate.now().isBefore(openDate)) {
			status = "미개강";
		}else if(LocalDate.now().isAfter(openDate) && LocalDate.now().isBefore(closeDate)) {
			status = "진행중";
		}else {
			status = "마감";
		}
		
		return status;
	}

	public boolean checkStatus(Course course) {
		boolean check = false;
		if(!course.getStatus().equals("마감") && LocalDate.now().isBefore(course.getCloseDate())) {
			check = true;
		}
		return check;
	}
	
	public void deleteCourse(CourseDTO.Delete dto) throws NotFoundException {
		Course course = findOne(dto.getIdx());
		courseRepository.deleteById(course.getIdx());
	}
	
	public Course findOne(Long courseIdx) throws NotFoundException {
		Course course = courseRepository.findById(courseIdx).orElseThrow(() -> new Exception.NotFoundException("Course with idx: " + courseIdx + " is not valid"));
	
		return course;
	}
	
	public List<Course> findAll() {
		return courseRepository.findAll();
	}
	
	public List<Course> findAllByTitleContaining(CourseDTO.Get dto) {
		return courseRepository.findCourseListByTitleContaining(dto.getTitle());
	}
	
	public List<Course> findAllBySubject(CourseDTO.Get dto) {
		return courseRepository.findCourseListBySubject(dto.getSubject());
	}
	
	public List<Course> findAllByTarget(CourseDTO.Get dto) {
		return courseRepository.findCourseListByTarget(dto.getTarget());
	}
	
	public List<Course> findAllBySchedule(CourseDTO.Get dto) {
		return courseRepository.findCourseListBySchedule(dto.getSchedule());
	}
	
	public List<Course> findAllByType(CourseDTO.Get dto) {
		return courseRepository.findCourseListByType(dto.getType());
	}
	
	public List<Course> findAllByTuition(CourseDTO.Get dto) {
		return courseRepository.findCourseListByTuition(dto.getTuition());
	}
	
}
