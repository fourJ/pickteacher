package kr.pe.fourj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Review;
import kr.pe.fourj.domain.Student;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	
	Review findByStudentIdxAndCourseIdx(Student student, Course course);
	
}