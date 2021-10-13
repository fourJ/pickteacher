package kr.pe.fourj.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Catalog;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.CatalogDTO;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.service.CatalogService;
import kr.pe.fourj.service.CourseService;

@RestController
public class CatalogController {
	
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private CourseService courseService;
	
	//수강 내역 저장
	@PostMapping("/catalog")
	public ResponseDTO.Create saveCatalog(HttpServletRequest request, @RequestBody CatalogDTO.Create dto) {
		System.out.println("-- 수강 내역 저장 시도 --");
		
		boolean result = false;
		Long saveId = null;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			
			try {
				Course course = courseService.findOne(dto.getCourseIdx());
				LocalDateTime dateTime = LocalDateTime.now();
				
				if(catalogService.isNotAlreadyCatalog(entity, course)) {
					try {
						saveId = catalogService.saveCatalog(new Catalog(entity, course, dateTime));
						result = true;
					} catch (ArgumentNullException e) {
						e.printStackTrace();
					}
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return new ResponseDTO.Create(saveId, result);
	}
	
	//수강 내역 삭제
	@DeleteMapping("/catalog")
	public ResponseDTO.Delete deleteCatalog(HttpServletRequest request, @RequestBody CatalogDTO.Delete dto) {
		System.out.println("-- 수강 내역 삭제 시도 --");
		
		boolean result = false;
		if(request.getSession().getAttribute("student") != null) { 
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			
			try {
				Course course = courseService.findOne(dto.getCourseIdx());
				catalogService.deleteCatalog(entity, course);
				result = true;
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return new ResponseDTO.Delete(result);
	}

	//???쓰일까요???
	//수강 내역 단일 검색
	@GetMapping("/catalog")
	public ResponseDTO.CatalogResponse findOneCatalog(@RequestBody CatalogDTO.Get dto) {
		System.out.println("-- 수강 내역 단일 검색 시도 --");
		
		boolean result = false;
		Catalog catalog = null;
		try {
			catalog = catalogService.findOne(dto.getIdx());
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.CatalogResponse(result, catalog);
	}
	
	//???쓰일까요???
	//수강 내역 전체 검색
	@GetMapping("/catalogall")
	public ResponseDTO.CatalogListResponse findAllCatalog() {
		System.out.println("-- 수강 내역 전체 검색 시도 --");
		
		List<Catalog> catalogList = catalogService.findAll();
		
		return new ResponseDTO.CatalogListResponse(true, catalogList);
	}
	
	//특정 학생(자신) id로 수강 내역 검색
	@GetMapping("/catalog/studentidx")
	public ResponseDTO.CatalogListResponse findAllByStudentIdx(HttpServletRequest request, @RequestBody CatalogDTO.Get dto) {
		System.out.println("-- 특정 학생이 수강한 내역 검색 시도 --");
		
		boolean result = false;
		List<Catalog> catalogList = null;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			catalogList = entity.getCatalogList();
			result = true;
		}
		
		return new ResponseDTO.CatalogListResponse(result, catalogList);
	}
	
	//???idx로 하는 검색이 쓰일까요???
	//특정 강의 id로 수강 내역 검색
	@GetMapping("/catalog/courseidx")
	public ResponseDTO.CatalogListResponse findAllByCourseIdx(@RequestBody CatalogDTO.Get dto) {
		System.out.println("-- 특정 강의 이름으로 수강 내역 검색 시도 --");
		
		boolean result = false;
		List<Catalog> catalogList = null;
		Course course;
		try {
			course = courseService.findOne(dto.getCourseIdx());
			catalogList = course.getCatalogList();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.CatalogListResponse(result, catalogList);
	}
	
}
