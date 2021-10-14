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
	public ResponseDTO.Create saveLikes(HttpServletRequest request, @RequestBody LikesDTO.Create dto) {
		System.out.println("-- 좋아요 저장 시도 --");
		
		boolean result = false;
		Long saveId = null;
		if(request.getSession().getAttribute("student") != null) {
			Object object = request.getSession().getAttribute("student");
			Student entity = (Student)object;

			try {
				Student student = studentService.findOne(entity.getIdx());
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
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
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
				Student student = studentService.findOne(entity.getIdx());
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
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}
		
		return new ResponseDTO.Delete(result);
	}
	
	//???idx로 하는 검색이 쓰일까요???
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
			
			try {
				Student student = studentService.findOne(entity.getIdx());
				likesList = student.getLikesList();
				result = true;
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}
		
		return new ResponseDTO.LikesListResponse(result, likesList);
	}

}
