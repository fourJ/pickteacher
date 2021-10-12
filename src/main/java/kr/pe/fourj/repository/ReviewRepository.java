package kr.pe.fourj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.fourj.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	
}