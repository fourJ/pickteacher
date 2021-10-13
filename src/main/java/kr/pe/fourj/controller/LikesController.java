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
			}	
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.Create(saveId, result);
	}
	
	//좋아요 삭제
	@DeleteMapping("/likes")
	public ResponseDTO.Delete deleteLikes(@RequestBody LikesDTO.Delete dto) {
		System.out.println("-- 좋아요 삭제 시도 --");

		boolean result = false;
		if(true) { //이 부분에 세션에서 정보 빼와서 dto 값과 확인 조건 필요
			try {
				likesService.deleteLikes(dto);
				result = true;
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return new ResponseDTO.Delete(result);
	}
	
	//좋아요 단일 검색
	@GetMapping("/likes")
	public ResponseDTO.LikesResponse findOne(@RequestBody LikesDTO.Get dto) {
		System.out.println("-- 좋아요 단일 검색 시도 --");
		
		boolean result = false;
		Likes likes = null;
		try {
			likes = likesService.findOne(dto.getIdx());
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.LikesResponse(result, likes);
	}
	
	//좋아요 리스트 전체 검색
	@GetMapping("/likesall")
	public ResponseDTO.LikesListResponse findAll(){
		System.out.println("-- 좋아요 리스트 전체 검색 시도 --");
		
		List<Likes> likesList = likesService.findAll();
		
		return new ResponseDTO.LikesListResponse(true, likesList);
	}
	
	//특정 학생이 누른 좋아요 리스트 검색
	@GetMapping("/likes/studentidx")
	public ResponseDTO.LikesListResponse findAllByStudentIdx(@RequestBody LikesDTO.Get dto) {
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
}
