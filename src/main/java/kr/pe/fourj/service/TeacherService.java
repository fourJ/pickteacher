package kr.pe.fourj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.fourj.domain.Teacher;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.dto.TeacherDTO;
import kr.pe.fourj.exception.Exception;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.TeacherRepository;

@Service
public class TeacherService {
	
	@Autowired
	private TeacherRepository teacherRepository;

	public Long saveTeacher(ResponseDTO.TeacherResponse teacherResponse) throws ArgumentNullException {
		Teacher save = null;

		if(teacherResponse.getTeacher() == null) {
			throw new Exception.ArgumentNullException("Teacher can't be null");
		}
		save = teacherRepository.save(teacherResponse.getTeacher());

		return save.getIdx();
	}

	public void updateTeacher(TeacherDTO.Update dto) throws NotFoundException {
		Teacher teacher = findOne(dto.getIdx()).getTeacher();

		teacher.setAddress(dto.getAddress());
		teacher.setPhone(dto.getPhone());
		
		teacherRepository.save(teacher);
	}	
	
	public void deleteTeacher(TeacherDTO.Delete dto) {
		teacherRepository.deleteById(dto.getIdx());
	}
	
	public ResponseDTO.TeacherResponse findOne(Long teacherIdx) throws NotFoundException {
		Teacher teacher = teacherRepository.findById(teacherIdx).orElseThrow(() -> new Exception.NotFoundException("Teacher with idx: " + teacherIdx + " is not valid"));
		
		return new ResponseDTO.TeacherResponse(true, teacher);
	}
	
	public ResponseDTO.TeacherListResponse findAll() {
		List<Teacher> teacherList = teacherRepository.findAll();
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	public ResponseDTO.TeacherListResponse findAllByName(TeacherDTO.Get dto) {
		List<Teacher> teacherList = teacherRepository.findByName(dto.getName());
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	public ResponseDTO.TeacherListResponse findAllByNameContaining(TeacherDTO.Get dto) {
		List<Teacher> teacherList = teacherRepository.findByNameContaining(dto.getName());
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	public ResponseDTO.TeacherListResponse findAllByGender(TeacherDTO.Get dto) {
		List<Teacher> teacherList = teacherRepository.findByGender(dto.getGender());
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	public ResponseDTO.TeacherListResponse findAllBySchool(TeacherDTO.Get dto) {
		List<Teacher> teacherList = teacherRepository.findBySchool(dto.getSchool());
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	public ResponseDTO.TeacherListResponse findAllBySchoolContaining(TeacherDTO.Get dto) {
		List<Teacher> teacherList = teacherRepository.findBySchoolContaining(dto.getSchool());
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	public ResponseDTO.TeacherListResponse findAllByMajor(TeacherDTO.Get dto) {
		List<Teacher> teacherList = teacherRepository.findByMajor(dto.getMajor());
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}
	
	public ResponseDTO.TeacherListResponse findAllByMajorContaining(TeacherDTO.Get dto) {
		List<Teacher> teacherList = teacherRepository.findByMajorContaining(dto.getMajor());
		
		return new ResponseDTO.TeacherListResponse(true, teacherList);
	}

}
