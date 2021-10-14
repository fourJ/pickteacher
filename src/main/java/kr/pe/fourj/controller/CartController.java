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
import kr.pe.fourj.service.StudentService;

@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private StudentService studentService;
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
				Student student = studentService.findOne(entity.getIdx());
				Course course = courseService.findOne(dto.getCourseIdx());
				
				if(cartService.isNotAlreadyCart(student, course) && courseService.checkStatus(course) && course.getHeadCount() > catalogService.findAllByCourseIdx(course).size()) {
					try {
						saveId = cartService.saveCart(new Cart(student, course));
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
	
	//카트 삭제
	@DeleteMapping("/cart")
	public ResponseDTO.Delete deleteCart(HttpServletRequest request, @RequestBody CartDTO.Delete dto) {
		System.out.println("-- 카트 삭제 시도 --");
		
		boolean result = false;
		if(request.getSession().getAttribute("student") != null) { 
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			
			try {
				Student student = studentService.findOne(entity.getIdx());
				Course course = courseService.findOne(dto.getCourseIdx());
				try {
					cartService.deleteCart(student, course);
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

	//특정 학생(자신)의 카트 리스트 검색
	@GetMapping("/cart/studentidx")
	public ResponseDTO.CartListResponse findAllByStudentIdx(HttpServletRequest request) {
		System.out.println("-- 특정 학생의 카트 리스트 검색 시도 --");
		
		boolean result = false;
		List<Cart> cartList = null;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			
			try {
				Student student = studentService.findOne(entity.getIdx());
				cartList = student.getCartList();
				result = true;
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
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
				Student student = studentService.findOne(entity.getIdx());
				Course course = courseService.findOne(dto.getCourseIdx());		
				LocalDateTime dateTime = LocalDateTime.now();
				
				if(catalogService.isNotAlreadyCatalog(student, course) && courseService.checkStatus(course) && course.getHeadCount() > catalogService.findAllByCourseIdx(course).size()) {
					try {
						boolean checkCart = cartService.isNotAlreadyCart(student, course);
						if(!checkCart) {
							saveId = catalogService.saveCatalog(new Catalog(student, course, dateTime));
							cartService.deleteCart(student, course);
							result = true;							
						}else {
							System.out.println("해당 강의가 장바구니에 없습니다.");
						}
					} catch (ArgumentNullException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("동일한 강의를 이미 수강신청 하셨거나, 마감일이 지났거나, 정원이 다 찼습니다.");
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}
		
		return new ResponseDTO.CartToCatalogResponse(saveId, result);
	}
	
	

}
