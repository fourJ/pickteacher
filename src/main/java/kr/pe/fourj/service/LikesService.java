package kr.pe.fourj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Likes;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.exception.Exception;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.LikesRepository;

@Service
public class LikesService {
	
	@Autowired
	private LikesRepository likesRepository;
	
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
        Likes likes = likesRepository.findByStudentIdxAndCourseIdx(student, course);
        flag = (likes == null)? true : false; 
        
        return flag;
    }
	
	public void deleteLikes(Student student, Course course) {
		Likes likes = likesRepository.findByStudentIdxAndCourseIdx(student, course);
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
