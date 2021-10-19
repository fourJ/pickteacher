package kr.pe.fourj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.StudentDTO;
import kr.pe.fourj.exception.Exception;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	public Long saveStudent(Student student) throws ArgumentNullException {
		Student save = null;

		if(student == null) {
			throw new Exception.ArgumentNullException("Student can't be null");
		}
		save = studentRepository.save(student);

		return save.getIdx();
	}

	public void updateStudent(StudentDTO.Update dto) throws NotFoundException {
		Student student = findOne(dto.getIdx());
		
		student.setPw(dto.getPw());
		student.setNickName(dto.getNickName());
		student.setAddress(dto.getAddress());
		student.setPhone(dto.getPhone());

		studentRepository.save(student);
	}
	
	public void deleteStudent(StudentDTO.Delete dto) throws NotFoundException {
		Student student = findOne(dto.getIdx());
		
		studentRepository.deleteById(student.getIdx());
	}
	
	public Student findOne(Long studentIdx) throws NotFoundException {
		Student student = studentRepository.findById(studentIdx).orElseThrow(() -> new Exception.NotFoundException("Student with idx: " + studentIdx + " is not valid"));
		return student;
	}
	
	public List<Student> findAll() {
		return studentRepository.findAll();
	}
	
	public Student findStudentById (String studentId) {
		Student student = studentRepository.findStudentById(studentId);
		return student;
	}
	
}
