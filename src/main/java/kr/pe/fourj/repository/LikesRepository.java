package kr.pe.fourj.repository;

import org.springframework.data.repository.CrudRepository;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Likes;
import kr.pe.fourj.domain.Student;

public interface LikesRepository extends CrudRepository<Likes, Long>{

	Likes findByStudentIdxAndCourseIdx(Student student, Course course);

}
