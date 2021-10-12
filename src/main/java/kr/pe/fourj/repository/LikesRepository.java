package kr.pe.fourj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.fourj.domain.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long>{

}
