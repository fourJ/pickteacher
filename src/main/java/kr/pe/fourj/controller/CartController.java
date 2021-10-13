package kr.pe.fourj.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Cart;
import kr.pe.fourj.domain.Catalog;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.CartDTO;
import kr.pe.fourj.repository.CartRepository;
import kr.pe.fourj.repository.CatalogRepository;
import kr.pe.fourj.repository.CourseRepository;
import kr.pe.fourj.repository.StudentRepository;

@RestController
public class CartController {
	
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CatalogRepository catalogRepository;
	
	//카트 저장
	@PostMapping("/cart")
	public String saveCart(@RequestBody CartDTO.Create dto) {
		System.out.println("-- 카트 정보 저장 시도 --");
		Student student = studentRepository.findById(dto.getStudentIdx()).get();
		Course course = courseRepository.findById(dto.getCourseIdx()).get();
		
		if(isNotAlreadyCart(student, course)) {
			cartRepository.save(new Cart(student, course));
			return "저장 성공";
		}else {
			return "저장 실패";
		}
	}
	
	//카트 삭제
	@DeleteMapping("/cart")
	public String deleteCart(@RequestBody CartDTO.Delete dto) {
		System.out.println("-- 카트 삭제 시도 --");
		
		cartRepository.deleteById(dto.getIdx());
		
		return "삭제 성공";
	}

	//특정 학생의 카트 조회
	@GetMapping("/cart")
	public List<Cart> findList(@RequestBody CartDTO.Get dto) {
		System.out.println("-- 특정 카트 목록 조회 시도 --");
		Student student = studentRepository.findById(dto.getStudentIdx()).get();
		
		return student.getCartList();
	}
	
	//수강 신청시 수강 내역으로 연동
	@PostMapping("/cart/send")
	public List<Catalog> sendCatalog(@RequestBody CartDTO.Get dto) {
		System.out.println("수강 내역으로 보내기 시도");
		Student student = studentRepository.findById(dto.getStudentIdx()).get();
		Course course = courseRepository.findById(dto.getCourseIdx()).get();
		LocalDateTime dateTime = LocalDateTime.now();
//		catalogRepository.save(new Catalog(student, course, dateTime));
		List<Catalog> catalogList = student.getCatalogList();
		
		cartRepository.deleteById(dto.getIdx());
		
		return catalogList;
	}
	
	private boolean isNotAlreadyCart(Student studentDTO , Course courseDTO) {
		boolean flag = false;
		Student student = studentRepository.findById(studentDTO.getIdx()).get();
		Course course = courseRepository.findById(courseDTO.getIdx()).get();
        Cart cart = cartRepository.findByStudentIdxAndCourseIdx(student, course);
        if(cart == null) flag = true;
        else if(cart != null) flag = false;
        return flag;
    }

}
