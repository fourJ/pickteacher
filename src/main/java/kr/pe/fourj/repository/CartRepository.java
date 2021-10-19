package kr.pe.fourj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.fourj.domain.Cart;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;

public interface CartRepository extends JpaRepository<Cart, Long>{

	 Cart findByStudentIdxAndCourseIdx(Student studentIdx, Course courseIdx);
	 
	
}
