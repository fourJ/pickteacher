package kr.pe.fourj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.fourj.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
}