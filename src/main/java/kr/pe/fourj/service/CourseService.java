package kr.pe.fourj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.dto.CourseDTO;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.exception.Exception;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	
	public Long saveCourse(ResponseDTO.CourseResponse courseResponse) throws ArgumentNullException {
		Course save = null;
		
		if(courseResponse.getCourse() == null) {
			throw new Exception.ArgumentNullException("Course can't be null");
		}
		save = courseRepository.save(courseResponse.getCourse());
		
		return save.getIdx();
	}
	
	public void updateCourse(CourseDTO.Update dto) throws NotFoundException {
		Course course = findOne(dto.getIdx()).getCourse();
		
		course.setSchedule(dto.getSchedule());
		course.setOpenDate(dto.getOpenDate());
		course.setCloseDate(dto.getCloseDate());
		course.setType(dto.getType());
		course.setStatus(dto.getStatus());
		course.setTuition(dto.getTuition());
		course.setTarget(dto.getTarget());
		
		courseRepository.save(course);
	}
	
	public void deleteCourse(CourseDTO.Delete dto) {
		courseRepository.deleteById(dto.getIdx());
	}
	
	public ResponseDTO.CourseResponse findOne(Long courseIdx) throws NotFoundException {
		Course course = courseRepository.findById(courseIdx).orElseThrow(() -> new Exception.NotFoundException("Course with idx: " + courseIdx + " is not valid"));
	
		return new ResponseDTO.CourseResponse(true, course);
	}
	
	public ResponseDTO.CourseListResponse findAll() {
		List<Course> courseList = courseRepository.findAll();
		
		return new ResponseDTO.CourseListResponse(true, courseList);
	}
	
	public ResponseDTO.CourseListResponse findAllByTitle(String title) {
		List<Course> courseList = courseRepository.findCourseListByTitleContaining(title);
		
		return new ResponseDTO.CourseListResponse(true, courseList);
	}
}
