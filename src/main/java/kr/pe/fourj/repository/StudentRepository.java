package kr.pe.fourj.repository;

import org.springframework.data.repository.CrudRepository;

import kr.pe.fourj.domain.Student;

public interface StudentRepository extends CrudRepository<Student, Long>{


}