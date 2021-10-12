package kr.pe.fourj.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.pe.fourj.domain.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Long>{
	
	List<Teacher> findByName(String name);
	List<Teacher> findByNameContaining(String name);
	List<Teacher> findByGender(Integer gender);
	List<Teacher> findBySchool(String school);
	List<Teacher> findBySchoolContaining(String school);
	List<Teacher> findByMajor(String major);
	List<Teacher> findByMajorContaining(String major);

}
