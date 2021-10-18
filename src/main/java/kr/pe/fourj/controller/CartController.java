package kr.pe.fourj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Cart;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.CartDTO;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.service.CartService;
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
	
	//카트 저장 => 프론트 연동 확인 필요 / 수강 정원 조건은 빼고 실험(이거 카탈로그에서 함)
	@PostMapping("/cart")
	public ResponseDTO.Create saveCart(@RequestBody CartDTO.Create dto) {
		System.out.println("-- 카트 저장 시도 --");
		
		boolean result = false;
		Long saveId = null;			
			try {
				Student student = studentService.findOne(dto.getStudentIdx());
				Course course = courseService.findOne(dto.getCourseIdx());
				
				if(cartService.isNotAlreadyCart(student, course) && courseService.checkStatus(course)) {
					try {
						saveId = cartService.saveCart(new Cart(student, course));
						result = true;
					} catch (ArgumentNullException e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("동일한 강의를 이미 수강신청 하셨거나, 정원이 다 찼습니다.");
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		
			return new ResponseDTO.Create(saveId, result);
	}

	//카트 삭제
	@DeleteMapping("/cart")
	public ResponseDTO.Delete deleteCart(CartDTO.Delete dto) {
		System.out.println("-- 카트 삭제 시도 --");

		boolean result = false;			
		try {
			Student student = studentService.findOne(dto.getStudentIdx());
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

		return new ResponseDTO.Delete(result);
	}

	//특정 학생(자신)의 카트 리스트 검색
	@GetMapping("/cart/studentidx")
	public ResponseDTO.CartListResponse findAllByStudentIdx(CartDTO.Get dto) {
		System.out.println("-- 특정 학생의 카트 리스트 검색 시도 --");

		boolean result = false;
		List<Cart> cartList = null;
		try {
			Student student = studentService.findOne(dto.getStudentIdx());
			cartList = student.getCartList();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseDTO.CartListResponse(result, cartList);
	}

}
