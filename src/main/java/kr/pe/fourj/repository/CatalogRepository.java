package kr.pe.fourj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.fourj.domain.Catalog;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;

public interface CatalogRepository extends JpaRepository<Catalog, Long>{

	Catalog findByStudentIdxAndCourseIdx(Student student, Course course);

	List<Catalog> findAllByCourseIdx(Course course);
}
