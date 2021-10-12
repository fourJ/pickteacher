package kr.pe.fourj.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import kr.pe.fourj.repository.CatalogRepository;
import kr.pe.fourj.repository.CourseRepository;
import kr.pe.fourj.repository.StudentRepository;

@RestController
public class CatalogController {
	
	@Autowired
	private CatalogRepository catalogRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;
	
	//수강 내역 저장
	@PostMapping("/catalog")
	public String saveCatalog(@RequestBody CatalogDTO.Create dto) {
		System.out.println("-- 수강 내역 저장 시도 --");
		Student student = studentRepository.findById(dto.getStudentIdx()).get();
		Course course = courseRepository.findById(dto.getCourseIdx()).get();
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(isNotAlreadyCatalog(student, course)) {
			catalogRepository.save(new Catalog(student, course, dateTime));
			return "저장 성공";
		}else {
			return "저장 실패";
		}
	}
	
	//수강 내역 삭제
	@DeleteMapping("/catalog")
	public String deleteCatalog(@RequestBody CatalogDTO.Delete dto) {
		System.out.println("-- 수강 내역 삭제 시도 --");
		
		catalogRepository.deleteById(dto.getIdx());
		
		return "삭제 성공";
	}

	//수강 내역 단일 검색
	@GetMapping("/catalog")
	public Catalog findOneCatalog(@RequestBody CatalogDTO.Get dto) {
		System.out.println("-- 수강 내역 단일 검색 시도 --");
		
		return catalogRepository.findById(dto.getIdx()).get();
	}
	
	//수강 내역 전체 검색
	@GetMapping("/catalogall")
	public List<Catalog> findAllCatalog() {
		System.out.println("-- 수강 내역 전체 검색 시도 --");
		List<Catalog> catalogList = new ArrayList<Catalog>();
		
		catalogRepository.findAll().forEach(e -> catalogList.add(e));
		
		return catalogList;
	}
	
	//특정 학생 id로 수강 내역 검색
	@GetMapping("/catalog/studentidx")
	public List<Catalog> findCatalogByStudentIdx(@RequestBody CatalogDTO.Get dto) {
		System.out.println("-- 특정 학생이 수강한 내역 검색 시도 --");
		Student student = studentRepository.findById(dto.getStudentIdx()).get();
		
		return student.getCatalogList();
	}
	
	//특정 강의 id로 수강 내역 검색
	@GetMapping("/catalog/courseidx")
	public List<Catalog> findCatalogByCourseIdx(@RequestBody CatalogDTO.Get dto) {
		System.out.println("-- 특정 강의 이름으로 수강 내역 검색 시도 --");
		Course course = courseRepository.findById(dto.getCourseIdx()).get();
		
		return course.getCatalogList();
	}
	
	//특정 학생 중복 수강 방지
	private boolean isNotAlreadyCatalog(Student studentDTO , Course courseDTO) {
		boolean flag = false;
		Student student = studentRepository.findById(studentDTO.getIdx()).get();
		Course course = courseRepository.findById(courseDTO.getIdx()).get();
        Catalog catalog = catalogRepository.findByStudentIdxAndCourseIdx(student, course);
        if(catalog == null) flag = true;
        else if(catalog != null) flag = false;
        return flag;
    }
	
}
