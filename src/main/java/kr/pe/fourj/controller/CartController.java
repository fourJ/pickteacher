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

@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CatalogService catalogService;
	
	//카트 저장
	@PostMapping("/cart")
	public ResponseDTO.Create saveCart(HttpServletRequest request, @RequestBody CartDTO.Create dto) {
		System.out.println("-- 카트 저장 시도 --");
		
		boolean result = false;
		Long saveId = null;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			
			try {
				Course course = courseService.findOne(dto.getCourseIdx());
				
				if(cartService.isNotAlreadyCart(entity, course)) {
					try {
						saveId = cartService.saveCart(new Cart(entity, course));
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
	
	//카트 삭제
	@DeleteMapping("/cart")
	public ResponseDTO.Delete deleteCart(HttpServletRequest request, @RequestBody CartDTO.Delete dto) {
		System.out.println("-- 카트 삭제 시도 --");
		
		boolean result = false;
		if(request.getSession().getAttribute("student") != null) { 
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			
			try {
				Course course = courseService.findOne(dto.getCourseIdx());
				cartService.deleteCart(entity, course);
				result = true;
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return new ResponseDTO.Delete(result);
	}

	//특정 학생(자신)의 카트 리스트 검색
	@GetMapping("/cart/studentidx")
	public ResponseDTO.CartListResponse findAllByStudentIdx(HttpServletRequest request) {
		System.out.println("-- 특정 학생의 카트 리스트 검색 시도 --");
		
		boolean result = false;
		List<Cart> cartList = null;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			cartList = entity.getCartList();
			result = true;
		}
		
		return new ResponseDTO.CartListResponse(result, cartList);
	}
	
	//수강 신청시 수강 내역으로 연동
	@PostMapping("/cart/send")
	public ResponseDTO.CartToCatalogResponse sendCatalog(HttpServletRequest request, @RequestBody CartDTO.Get dto) {
		System.out.println("-- 수강 내역으로 보내기 시도 --");
		
		boolean result = false;
		Long saveId = null;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			
			try {
				Course course = courseService.findOne(dto.getStudentIdx());		
				LocalDateTime dateTime = LocalDateTime.now();
				
				try {
					saveId = catalogService.saveCatalog(new Catalog(entity, course, dateTime));
					cartService.deleteCart(entity, course);
					result = true;
				} catch (ArgumentNullException e) {
					e.printStackTrace();
				}
				
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return new ResponseDTO.CartToCatalogResponse(saveId, result);
	}
	
	

}
