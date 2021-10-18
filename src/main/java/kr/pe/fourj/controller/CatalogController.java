package kr.pe.fourj.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	//수강 내역 저장(수강신청)
	@PostMapping("/catalog")
	public ResponseDTO.Create saveCatalog(@RequestBody CatalogDTO.Create dto) {
		System.out.println("-- 수강 내역 저장 시도 --");
		
		boolean result = false;
		Long saveId = null;			
			try {
				Student student = studentService.findOne(dto.getStudentIdx());
				Course course = courseService.findOne(dto.getCourseIdx());
				LocalDate date = LocalDate.now();
				
				if(catalogService.isNotAlreadyCatalog(student, course)) {
					try {
						saveId = catalogService.saveCatalog(new Catalog(student, course, date));
						result = true;
					} catch (ArgumentNullException e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("동일한 강의를 이미 수강하고 있습니다.");
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		
		return new ResponseDTO.Create(saveId, result);
	}
	
	//특정 학생(자신) id로 수강 내역 검색
	@GetMapping("/catalog/studentidx")
	public ResponseDTO.CatalogListResponse findAllByStudentIdx(CatalogDTO.Get dto) {
		System.out.println("-- 특정 학생이 수강한 내역 검색 시도 --");
		
		boolean result = false;
		List<Catalog> catalogList = null;			
		try {
			Student student = studentService.findOne(dto.getStudentIdx());
			catalogList = student.getCatalogList();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseDTO.CatalogListResponse(result, catalogList);
	}
	
	//특정 강의 id로 수강 내역 검색
	@GetMapping("/catalog/courseidx")
	public ResponseDTO.CatalogListResponse findAllByCourseIdx(CatalogDTO.Get dto) {
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
