package kr.pe.fourj.service;

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
	
	public void updateCourse(CourseDTO.Update dto) throws NotFoundException {
		Course course = findOne(dto.getIdx());
		
		course.setSchedule(dto.getSchedule());
		course.setOpenDate(dto.getOpenDate());
		course.setCloseDate(dto.getCloseDate());
		course.setType(dto.getType());
		course.setStatus(dto.getStatus());
		course.setTuition(dto.getTuition());
		course.setTarget(dto.getTarget());
		
		courseRepository.save(course);
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
	
	public List<Course> findAllByTitle(String title) {
		return courseRepository.findCourseListByTitleContaining(title);
	}
}
