package kr.pe.fourj.service;

import java.util.Date;
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
		course.setType(dto.getType());
		course.setTuition(dto.getTuition());
		course.setTarget(dto.getTarget());
		
		courseRepository.save(course);
	}
	
	public String calculateStatus(Date openDate, Date closeDate) {
		Date now = new Date();
		System.out.println("현재 시간 : " + now);
		String status = null;

		if(now.getTime() <= openDate.getTime()) {
			status = "미개강";
		}else if(now.getTime() >= openDate.getTime() && now.getTime() <= closeDate.getTime()) {
			status = "진행중";
		}else {
			status = "마감";
		}
		
		return status;
	}

	public boolean checkStatus(Course course) {
		Date now = new Date();
		boolean check = false;
		if(!course.getStatus().equals("마감") && now.getTime() <= course.getCloseDate().getTime()) {
			check = true;
		}
		return check;
	}
	
	public void deleteCourse(Long courseIdx, CourseDTO.Delete dto) throws NotFoundException {
		Course course = findOne(courseIdx);
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
	
}
