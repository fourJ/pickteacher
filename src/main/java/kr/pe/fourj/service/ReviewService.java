package kr.pe.fourj.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.fourj.domain.Review;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.dto.ReviewDTO;
import kr.pe.fourj.exception.Exception;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	public Long saveReview(ResponseDTO.ReviewResponse reviewResponse) throws ArgumentNullException {
		Review save = null;

		if(reviewResponse.getReview() == null) {
			throw new Exception.ArgumentNullException("Review can't be null");
		}
		save = reviewRepository.save(reviewResponse.getReview());			

		return save.getIdx();
	}
	
	public void updateReview(ReviewDTO.Update dto) throws NotFoundException {
		Review review = findOne(dto.getIdx()).getReview();
		LocalDateTime dateTime = LocalDateTime.now();

		review.setContent(dto.getContent());
		review.setDateTime(dateTime);
		review.setStar(dto.getStar());

		reviewRepository.save(review);
	}
	
	public void deleteReview(ReviewDTO.Delete dto) {
		reviewRepository.deleteById(dto.getIdx());
	}
	
	public ResponseDTO.ReviewResponse findOne(Long reviewIdx) throws NotFoundException {
		Review review = reviewRepository.findById(reviewIdx).orElseThrow(() -> new Exception.NotFoundException("Review with idx: " + reviewIdx + " is not valid"));
		
		return new ResponseDTO.ReviewResponse(true, review);
	}
	
	public ResponseDTO.ReviewListResponse findAll() {
		List<Review> reviewList = reviewRepository.findAll();
		
		return new ResponseDTO.ReviewListResponse(true, reviewList);
	}
}
