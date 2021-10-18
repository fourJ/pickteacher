package kr.pe.fourj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.pe.fourj.domain.Teacher;
import kr.pe.fourj.dto.TeacherDTO;
import kr.pe.fourj.exception.Exception;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.repository.TeacherRepository;

@Service
public class TeacherService {
	
	@Autowired
	private TeacherRepository teacherRepository;

	public Long saveTeacher(Teacher teacher) throws ArgumentNullException {
		Teacher save = null;

		if(teacher == null) {
			throw new Exception.ArgumentNullException("Teacher can't be null");
		}
		save = teacherRepository.save(teacher);

		return save.getIdx();
	}

	public void updateTeacher(TeacherDTO.Update dto) throws NotFoundException {
		Teacher teacher = findOne(dto.getIdx());
		
		teacher.setPw(dto.getPw());
		teacher.setAddress(dto.getAddress());
		teacher.setPhone(dto.getPhone());
		teacher.setCareer(dto.getCareer());
		
		teacherRepository.save(teacher);
	}	
	
	public void deleteTeacher(TeacherDTO.Delete dto) throws NotFoundException {
		Teacher teacher = findOne(dto.getIdx());
		teacherRepository.deleteById(teacher.getIdx());
	}
	
	public Teacher findOne(Long teacherIdx) throws NotFoundException {
		Teacher teacher = teacherRepository.findById(teacherIdx).orElseThrow(() -> new Exception.NotFoundException("Teacher with idx: " + teacherIdx + " is not valid"));
		
		return teacher;
	}
	
	public Teacher findTeacherById(String teacherId) {
		Teacher teacher = teacherRepository.findTeacherById(teacherId);
		return teacher;
	}
	
	public List<Teacher> findAll() {
		return teacherRepository.findAll();
	}
	
	public List<Teacher> findAllByName(TeacherDTO.Get dto) {
		return teacherRepository.findByName(dto.getName());
	}
	
	public List<Teacher> findAllByNameContaining(TeacherDTO.Get dto) {
		return teacherRepository.findByNameContaining(dto.getName());
	}
	
	public List<Teacher> findAllByGender(TeacherDTO.Get dto) {
		return teacherRepository.findByGender(dto.getGender());
	}
	
	public List<Teacher> findAllByCareer(TeacherDTO.Get dto) {
		return teacherRepository.findByCareer(dto.getCareer());
	}
	
	public List<Teacher> findAllBySchool(TeacherDTO.Get dto) {
		return teacherRepository.findBySchool(dto.getSchool());
	}
	
	public List<Teacher> findAllBySchoolContaining(TeacherDTO.Get dto) {
		return teacherRepository.findBySchoolContaining(dto.getSchool());
	}
	
	public List<Teacher> findAllByMajor(TeacherDTO.Get dto) {
		return teacherRepository.findByMajor(dto.getMajor());
	}
	
	public List<Teacher> findAllByMajorContaining(TeacherDTO.Get dto) {
		return teacherRepository.findByMajorContaining(dto.getMajor());
	}

}
