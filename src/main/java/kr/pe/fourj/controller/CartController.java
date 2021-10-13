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
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.service.CartService;
import kr.pe.fourj.service.CatalogService;
import kr.pe.fourj.service.CourseService;
import kr.pe.fourj.service.StudentService;

@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CatalogService catalogService;
	
	//카트 저장
	@PostMapping("/cart")
	public ResponseDTO.Create saveCart(@RequestBody CartDTO.Create dto) {
		System.out.println("-- 카트 저장 시도 --");
		
		boolean result = false;
		Long saveId = null;
		try {
			Student student = studentService.findOne(dto.getStudentIdx());
			Course course = courseService.findOne(dto.getCourseIdx());
			
			if(cartService.isNotAlreadyCart(student, course)) {
				try {
					saveId = cartService.saveCart(new Cart(student, course));
					result = true;
				} catch (ArgumentNullException e) {
					e.printStackTrace();
				}
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.Create(saveId, result);
	}
	
	//카트 삭제
	@DeleteMapping("/cart")
	public ResponseDTO.Delete deleteCart(@RequestBody CartDTO.Delete dto) {
		System.out.println("-- 카트 삭제 시도 --");
		
		boolean result = false;
		try {
			cartService.deleteCart(dto.getIdx());
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.Delete(result);
	}

	//특정 학생의 카트 리스트 검색
	@GetMapping("/cart/studentidx")
	public ResponseDTO.CartListResponse findAllByStudentIdx(@RequestBody CartDTO.Get dto) {
		System.out.println("-- 특정 학생의 카트 리스트 검색 시도 --");
		
		boolean result = false;
		List<Cart> cartList = null;
		
		Student student;
		try {
			student = studentService.findOne(dto.getStudentIdx());
			cartList = student.getCartList();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.CartListResponse(result, cartList);
	}
	
	//수강 신청시 수강 내역으로 연동
	@PostMapping("/cart/send")
	public ResponseDTO.CartToCatalogResponse sendCatalog(@RequestBody CartDTO.Get dto) {
		System.out.println("-- 수강 내역으로 보내기 시도 --");
		
		boolean result = false;
		Long saveId = null;
		List<Cart> cartList = null;
		List<Catalog> catalogList = null;
		try {
			Student student = studentService.findOne(dto.getStudentIdx());
			Course course = courseService.findOne(dto.getStudentIdx());		
			LocalDateTime dateTime = LocalDateTime.now();
			
			try {
				saveId = catalogService.saveCatalog(new Catalog(student, course, dateTime));
				cartService.deleteCart(dto.getIdx());
				result = true;
			} catch (ArgumentNullException e) {
				e.printStackTrace();
			}
			
			cartList = student.getCartList();
			catalogList = student.getCatalogList();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.CartToCatalogResponse(saveId, result, cartList, catalogList);
	}
	
	

}
