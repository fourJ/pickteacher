package kr.pe.fourj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.fourj.domain.Cart;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.exception.Exception;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.CartRepository;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	public Long saveCart(Cart cart) throws ArgumentNullException {
		Cart save = null;
		
		if(cart == null) {
			throw new Exception.ArgumentNullException("Cart can't be null");
		}
		save = cartRepository.save(cart);
		
		return save.getIdx();
	}
	
	public boolean isNotAlreadyCart(Student student , Course course) {
		boolean flag = false;
        Cart cart = cartRepository.findByStudentIdxAndCourseIdx(student, course);
        flag = (cart == null)? true : false; 
        
        return flag;
    }
	
	public void deleteCart(Student student, Course course) throws NotFoundException, ArgumentNullException {
		Cart cart = cartRepository.findByStudentIdxAndCourseIdx(student, course);
		if(cart != null) {
			cartRepository.deleteById(cart.getIdx());
		}else {
			throw new Exception.ArgumentNullException("Cart with " + student.getId() + " and " + course.getTitle() + " is not valid");
		}
	}
	
	public Cart findOne(Long cartIdx) throws NotFoundException {
		Cart cart = cartRepository.findById(cartIdx).orElseThrow(() -> new Exception.NotFoundException("Cart with idx: " + cartIdx + " is not valid"));
		
		return cart;
	}
	
}
