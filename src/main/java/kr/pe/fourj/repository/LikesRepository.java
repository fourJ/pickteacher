package kr.pe.fourj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Likes;
import kr.pe.fourj.domain.Student;

public interface LikesRepository extends JpaRepository<Likes, Long>{

	Likes findByStudentIdxAndCourseIdx(Student student, Course course);

}
