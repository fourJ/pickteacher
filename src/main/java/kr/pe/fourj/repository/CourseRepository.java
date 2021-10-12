package kr.pe.fourj.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.pe.fourj.domain.Course;

public interface CourseRepository extends CrudRepository<Course, Long>{
	
//	Course findCourseByTitle(String title);
//	Course findCourseByTitleContaining(String title);
//	Course findCourseBySubject(String subject);
//	Course findCourseBySchedule(String schedule);
//	Course findCourseByType(String type);
//	Course findCourseByStatus(String status);
//	Course findCourseByTuition(String tuition);
//	Course findCourseByTarget(String target);
	List<Course> findCourseListByTitle(String title);
	List<Course> findCourseListByTitleContaining(String title);
//	List<Course> findCourseListBySubject(String subject);
//	List<Course> findCourseListBySchedule(String schedule);
//	List<Course> findCourseListByType(String type);
//	List<Course> findCourseListByStatus(String status);
//	List<Course> findCourseListByTuition(String tuition);
//	List<Course> findCourseListByTarget(String target);

}
