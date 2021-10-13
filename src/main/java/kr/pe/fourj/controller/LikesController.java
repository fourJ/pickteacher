package kr.pe.fourj.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

@RestController
public class LikesController {
	
	@Autowired
	private LikesService likesService;
	@Autowired
	private CourseService courseService;
	
	
	//좋아요 저장
	@PostMapping("/likes")
	public ResponseDTO.Create saveLikes(HttpServletRequest request, @RequestBody LikesDTO.Create dto) {
		System.out.println("-- 좋아요 저장 시도 --");
		
		boolean result = false;
		Long saveId = null;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;

			try {
				Course course = courseService.findOne(dto.getCourseIdx());

				if(likesService.isNotAlreadyLikes(entity, course)) {
					try {
						saveId = likesService.saveLikes(new Likes(entity, course));
						result = true;
					} catch (ArgumentNullException e) {
						e.printStackTrace();
					}
				}	
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return new ResponseDTO.Create(saveId, result);
	}
	
	//좋아요 삭제
	@DeleteMapping("/likes")
	public ResponseDTO.Delete deleteLikes(HttpServletRequest request, @RequestBody LikesDTO.Delete dto) {
		System.out.println("-- 좋아요 삭제 시도 --");

		boolean result = false;
		if(request.getSession().getAttribute("student") != null) { 
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			
			try {
				Course course = courseService.findOne(dto.getCourseIdx());
				likesService.deleteLikes(entity, course);
				result = true;
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return new ResponseDTO.Delete(result);
	}
	
	//???쓰일까요???
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
	
	//???쓰일까요???
	//좋아요 리스트 전체 검색
	@GetMapping("/likesall")
	public ResponseDTO.LikesListResponse findAll(){
		System.out.println("-- 좋아요 리스트 전체 검색 시도 --");
		
		List<Likes> likesList = likesService.findAll();
		
		return new ResponseDTO.LikesListResponse(true, likesList);
	}
	
	//특정 학생(자신)이 누른 좋아요 리스트 검색
	@GetMapping("/likes/studentidx")
	public ResponseDTO.LikesListResponse findAllByStudentIdx(HttpServletRequest request) {
		System.out.println("-- 특정 학생이 누른 좋아요 리스트 전체 검색 시도 --");
		
		boolean result = false;
		List<Likes> likesList = null;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;
			likesList = entity.getLikesList();
			result = true;
		}
		
		return new ResponseDTO.LikesListResponse(result, likesList);
	}

}
