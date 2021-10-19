package kr.pe.fourj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Likes;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.dto.LikesDTO;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.service.CourseService;
import kr.pe.fourj.service.LikesService;
import kr.pe.fourj.service.StudentService;

@RestController
public class LikesController {
	
	@Autowired
	private LikesService likesService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private CourseService courseService;
	
	
	//좋아요 저장
	@PostMapping("/likes")
	public ResponseDTO.Create saveLikes(@RequestBody LikesDTO.Create dto) {
		System.out.println("-- 좋아요 저장 시도 --");

		boolean result = false;
		Long saveId = null;
		try {
			Student student = studentService.findOne(dto.getStudentIdx());
			Course course = courseService.findOne(dto.getCourseIdx());

			if(likesService.isNotAlreadyLikes(student, course)) {
				try {
					saveId = likesService.saveLikes(new Likes(student, course));
					result = true;
				} catch (ArgumentNullException e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("동일한 강의에는 최대 한번만 좋아요를 누를 수 있습니다.");
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseDTO.Create(saveId, result);
	}
	
	//좋아요 삭제
	@DeleteMapping("/likes")
	public ResponseDTO.Delete deleteLikes(LikesDTO.Delete dto) {
		System.out.println("-- 좋아요 삭제 시도 --");

		boolean result = false;			
		try {
			Student student = studentService.findOne(dto.getStudentIdx());
			Course course = courseService.findOne(dto.getCourseIdx());
			try {
				likesService.deleteLikes(student, course);
				result = true;
			} catch (ArgumentNullException e) {
				e.printStackTrace();
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.Delete(result);
	}
	
	//특정 학생(자신)이 누른 좋아요 리스트 검색
	@GetMapping("/likes/studentidx")
	public ResponseDTO.LikesListResponse findAllByStudentIdx(LikesDTO.Get dto) {
		System.out.println("-- 특정 학생이 누른 좋아요 리스트 전체 검색 시도 --");	
		
		boolean result = false;
		List<Likes> likesList = null;	
		try {
			Student student = studentService.findOne(dto.getStudentIdx());
			likesList = student.getLikesList();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.LikesListResponse(result, likesList);
	}
	
	//좋아요 눌렀는지 안눌렀는지 찾아서 return
	@GetMapping("/likes/check")
	public ResponseDTO.LikesCheckResponse checkLikes(LikesDTO.Get dto) {
		System.out.println("-- 특정 학생이 특정 과목 좋아요 눌렀는지 체크 시도 --");
		
		boolean result = false;
		Integer check = -1;
		try {
			Student student = studentService.findOne(dto.getStudentIdx());
			Course course = courseService.findOne(dto.getCourseIdx());
			if(likesService.isNotAlreadyLikes(student, course)) {
				check = 0; //좋아요 안 누름
			} else {
				check = 1; //좋아요 누름
			}
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.LikesCheckResponse(result, check);
	}

}
