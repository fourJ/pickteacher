package kr.pe.fourj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.fourj.domain.Catalog;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.exception.Exception;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.CatalogRepository;

@Service
public class CatalogService {
	
	@Autowired
	private CatalogRepository catalogRepository;
	
	public Long saveCatalog(Catalog catalog) throws ArgumentNullException {
		Catalog save = null;
		
		if(catalog == null) {
			throw new Exception.ArgumentNullException("Catalog can't be null");
		}
		save = catalogRepository.save(catalog);
		
		return save.getIdx();
	}
	
	public boolean isNotAlreadyCatalog(Student student , Course course) {
		boolean flag = false;
		Catalog catalog = catalogRepository.findByStudentIdxAndCourseIdx(student, course);
		flag = (catalog == null)? true : false; 
		
		return flag;
	}
	
	public void deleteCatalog(Student student, Course course) throws NotFoundException, ArgumentNullException {
		Catalog catalog = catalogRepository.findByStudentIdxAndCourseIdx(student, course);
		if(catalog != null) {
			catalogRepository.deleteById(catalog.getIdx());
		}else {
			throw new Exception.ArgumentNullException("Catalog with " + student.getId() + " and " + course.getTitle() + " is not valid");
		}
	}
	
	public Catalog findOne(Long catalogIdx) throws NotFoundException {
		Catalog catalog = catalogRepository.findById(catalogIdx).orElseThrow(() -> new Exception.NotFoundException("Catalog with idx: " + catalogIdx + " is not valid"));
		
		return catalog;
	}
	
	public List<Catalog> findAll() {
		return catalogRepository.findAll();
	}
	
	public List<Catalog> findAllByCourseIdx(Course course) {
		return catalogRepository.findAllByCourseIdx(course);
	}
	
}
