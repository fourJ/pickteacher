package kr.pe.fourj.repository;

import org.springframework.data.repository.CrudRepository;

import kr.pe.fourj.domain.Review;

public interface ReviewRepository extends CrudRepository<Review, Long>{

}