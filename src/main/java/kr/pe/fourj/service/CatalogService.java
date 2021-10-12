package kr.pe.fourj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.fourj.domain.Catalog;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.repository.CatalogRepository;

@Service
public class CatalogService {
	
	@Autowired
	private CatalogRepository catalogRepository; 
	
	public ResponseDTO.CatalogListResponse findAllByCourseIdx(Long idx) {
		List<Catalog> catalogList = catalogRepository.findAllByCourseIdx(idx);
		
		return new ResponseDTO.CatalogListResponse(true, catalogList);
	}
}
