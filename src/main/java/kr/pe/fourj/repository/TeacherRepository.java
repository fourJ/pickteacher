package kr.pe.fourj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.fourj.domain.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{
	
	List<Teacher> findByName(String name);
	List<Teacher> findByNameContaining(String name);
	List<Teacher> findByGender(String gender);
	List<Teacher> findByCareer(String career);
	List<Teacher> findBySchool(String school);
	List<Teacher> findBySchoolContaining(String school);
	List<Teacher> findByMajor(String major);
	List<Teacher> findByMajorContaining(String major);
	Teacher findTeacherById(String id);
}
