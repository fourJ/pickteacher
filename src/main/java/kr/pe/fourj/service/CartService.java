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
import kr.pe.fourj.repository.CourseRepository;
import kr.pe.fourj.repository.StudentRepository;

@Service
public class CartService {
	
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;
	
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
		Student findStudent = studentRepository.findById(student.getIdx()).get();
		Course findCourse = courseRepository.findById(course.getIdx()).get();
        Cart cart = cartRepository.findByStudentIdxAndCourseIdx(findStudent, findCourse);
        flag = (cart == null)? true : false; 
        
        return flag;
    }
	
	public void deleteCart(Long idx) throws NotFoundException {
		Cart cart = findOne(idx);
		cartRepository.deleteById(cart.getIdx());
	}
	
	public Cart findOne(Long cartIdx) throws NotFoundException {
		Cart cart = cartRepository.findById(cartIdx).orElseThrow(() -> new Exception.NotFoundException("Cart with idx: " + cartIdx + " is not valid"));
		
		return cart;
	}
	
}
