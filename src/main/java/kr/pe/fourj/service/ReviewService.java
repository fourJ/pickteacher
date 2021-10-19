package kr.pe.fourj.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Review;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.ReviewDTO;
import kr.pe.fourj.exception.Exception;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	public Long saveReview(Review review) throws ArgumentNullException {
		Review save = null;

		if(review == null) {
			throw new Exception.ArgumentNullException("Review can't be null");
		}
		save = reviewRepository.save(review);			

		return save.getIdx();
	}
	
	public boolean isNotAlreadyReview(Student student, Course course) {
		boolean flag = false;
        Review review = reviewRepository.findByStudentIdxAndCourseIdx(student, course);
        flag = (review == null)? true : false; 
        
        return flag;
    }
	
	public void updateReview(ReviewDTO.Update dto) throws NotFoundException {
		Review review = findOne(dto.getIdx());
		LocalDate date = LocalDate.now();

		review.setContent(dto.getContent());
		review.setDate(date);
		review.setStar(dto.getStar());

		reviewRepository.save(review);
	}
	
	public void deleteReview(Student student, Course course) throws NotFoundException {
		Review review = reviewRepository.findByStudentIdxAndCourseIdx(student, course);
		reviewRepository.deleteById(review.getIdx());
	}
	
	public Review findOne(Long reviewIdx) throws NotFoundException {
		Review review = reviewRepository.findById(reviewIdx).orElseThrow(() -> new Exception.NotFoundException("Review with idx: " + reviewIdx + " is not valid"));
		
		return review;
	}
	
	public List<Review> findAll() {
		return reviewRepository.findAll();
	}
}