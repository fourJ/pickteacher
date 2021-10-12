package kr.pe.fourj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.dto.StudentDTO;
import kr.pe.fourj.exception.Exception;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	public Long saveStudent(ResponseDTO.StudentResponse studentResponse) throws ArgumentNullException {
		Student save = null;

		if(studentResponse.getStudent() == null) {
			throw new Exception.ArgumentNullException("Student can't be null");
		}
		save = studentRepository.save(studentResponse.getStudent());

		return save.getIdx();
	}

	public void updateStudent(StudentDTO.Update dto) throws NotFoundException {
		Student student = findOne(dto.getIdx()).getStudent();
		
		student.setNickName(dto.getNickName());
		student.setAddress(dto.getAddress());
		student.setPhone(dto.getPhone());

		studentRepository.save(student);
	}
	
	public void deleteStudent(StudentDTO.Delete dto) {
		studentRepository.deleteById(dto.getIdx());
	}
	
	public ResponseDTO.StudentResponse findOne(Long studentIdx) throws NotFoundException {
		Student student = studentRepository.findById(studentIdx).orElseThrow(() -> new Exception.NotFoundException("Student with idx: " + studentIdx + " is not valid"));
	
		return new ResponseDTO.StudentResponse(true, student);
	}
	
	public ResponseDTO.StudentListResponse findAll() {
		List<Student> studentList = studentRepository.findAll();	
		
		return new ResponseDTO.StudentListResponse(true, studentList);
	}
}
