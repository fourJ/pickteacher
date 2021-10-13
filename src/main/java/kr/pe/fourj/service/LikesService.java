package kr.pe.fourj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Likes;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.LikesDTO;
import kr.pe.fourj.exception.Exception;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.CourseRepository;
import kr.pe.fourj.repository.LikesRepository;
import kr.pe.fourj.repository.StudentRepository;

@Service
public class LikesService {
	
	@Autowired
	private LikesRepository likesRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;
	
	public Long saveLikes(Likes likes) throws ArgumentNullException {
		Likes save = null;
		
		if(likes == null) {
			throw new Exception.ArgumentNullException("Likes can't be null");
		}
		save = likesRepository.save(likes);
		
		return save.getIdx();
	}
	
	public boolean isNotAlreadyLikes(Student student, Course course) {
		boolean flag = false;
		Student findStudent = studentRepository.findById(student.getIdx()).get();
		Course findCourse = courseRepository.findById(course.getIdx()).get();
        Likes likes = likesRepository.findByStudentIdxAndCourseIdx(findStudent, findCourse);
        flag = (likes == null)? true : false; 
        
        return flag;
    }
	
	public void deleteLikes(LikesDTO.Delete dto) throws NotFoundException {
		Likes likes = findOne(dto.getIdx());
		likesRepository.deleteById(likes.getIdx());
	}
	
	public Likes findOne(Long likesIdx) throws NotFoundException {
		Likes likes = likesRepository.findById(likesIdx).orElseThrow(() -> new Exception.NotFoundException("Likes with idx: " + likesIdx + " is not valid"));
		
		return likes;
	}
	
	public List<Likes> findAll() {
		return likesRepository.findAll();
	}
}
