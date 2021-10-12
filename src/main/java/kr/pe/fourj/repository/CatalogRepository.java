package kr.pe.fourj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.fourj.domain.Catalog;

public interface CatalogRepository extends JpaRepository<Catalog, Long>{
	
	List<Catalog> findAllByCourseIdx(Long courseIdx);
}
