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
import kr.pe.fourj.service.StudentService;

@RestController
public class CatalogController {
	
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private StudentService studentService;
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
				Student student = studentService.findOne(entity.getIdx());
				Course course = courseService.findOne(dto.getCourseIdx());
				LocalDateTime dateTime = LocalDateTime.now();
				
				if(catalogService.isNotAlreadyCatalog(student, course) && courseService.checkStatus(course) && course.getHeadCount() > catalogService.findAllByCourseIdx(course).size()) {
					try {
						saveId = catalogService.saveCatalog(new Catalog(student, course, dateTime));
						result = true;
					} catch (ArgumentNullException e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("동일한 강의를 이미 수강신청 하셨거나, 마감일이 지났거나, 정원이 다 찼습니다.");
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
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
				Student student = studentService.findOne(entity.getIdx());
				Course course = courseService.findOne(dto.getCourseIdx());
				try {
					catalogService.deleteCatalog(student, course);
					result = true;
				} catch (ArgumentNullException e) {
					e.printStackTrace();
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}
		
		return new ResponseDTO.Delete(result);
	}

	//???쓰일까요???
	//수강 내역 단일 검색
	@GetMapping("/catalog")
	public ResponseDTO.CatalogResponse findOne(@RequestBody CatalogDTO.Get dto) {
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
	public ResponseDTO.CatalogListResponse findAll() {
		System.out.println("-- 수강 내역 전체 검색 시도 --");
		
		List<Catalog> catalogList = catalogService.findAll();
		
		return new ResponseDTO.CatalogListResponse(true, catalogList);
	}
	
	//특정 학생(자신) id로 수강 내역 검색
	@GetMapping("/catalog/studentidx")
	public ResponseDTO.CatalogListResponse findAllByStudentIdx(HttpServletRequest request) {
		System.out.println("-- 특정 학생이 수강한 내역 검색 시도 --");
		
		boolean result = false;
		List<Catalog> catalogList = null;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			
			try {
				Student student = studentService.findOne(entity.getIdx());
				catalogList = student.getCatalogList();
				result = true;
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
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
