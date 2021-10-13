package kr.pe.fourj.controller;

import java.util.ArrayList;
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
import kr.pe.fourj.dto.CourseDTO;
import kr.pe.fourj.dto.LikesDTO;
import kr.pe.fourj.dto.StudentDTO;
import kr.pe.fourj.repository.CourseRepository;
import kr.pe.fourj.repository.LikesRepository;
import kr.pe.fourj.repository.StudentRepository;

@RestController
public class LikesController {

	@Autowired
	private LikesRepository likesRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	

    
	
	//좋아요 추가
	@PostMapping("/likes")
	public String saveLikes(@RequestBody LikesDTO.Create dto) {
		System.out.println("-- 좋아요 추가 시도--");
		Student student = studentRepository.findById(dto.getStudentIdx()).get();
		Course course = courseRepository.findById(dto.getCourseIdx()).get();
		
		if( isNotAlreadyLikes(student, course)) {
			likesRepository.save(new Likes(student,course));
			return "저장 성공";
		}else {
			return "저장실패";
		}	
	}
	
	private boolean isNotAlreadyLikes(Student studentDTO , Course courseDTO) {
		boolean flag = false;
		Student student = studentRepository.findById(studentDTO.getIdx()).get();
		Course course = courseRepository.findById(courseDTO.getIdx()).get();
        Likes likes = likesRepository.findByStudentIdxAndCourseIdx(student, course);
        if(likes == null) flag = true;
        else if(likes != null) flag = false;
        return flag;
    }

	
	//좋아요 삭제
	@DeleteMapping("/likes")
	public String deleteLikes(@RequestBody LikesDTO.Get dto, StudentDTO.Get studentDto, CourseDTO.Get courseDto) {
		System.out.println("좋아요 삭제 시도");

		if(dto.getStudentIdx()==studentDto.getIdx() || dto.getCourseIdx()==courseDto.getIdx()) {
			likesRepository.deleteById(dto.getIdx());
			return "삭제 성공!";
		}
		return "삭제 실패!";
	}
	
	//좋아요 단일 조회
	@GetMapping("likes")
	public Likes findOne(@RequestBody LikesDTO.Get dto) {
//	public Likes findOne(@RequestParam Long id) {
		System.out.println("--좋아요 단일 검색 시도--");
		
		return likesRepository.findById(dto.getIdx()).get();
		
//		return likesRepository.findById(id).get();
	}
	
	//좋아요 전체보기
	@GetMapping("/likesall")
	public List<Likes> findAll(){
		System.out.println("좋아요 전체 검색 시도");
		List<Likes> likesList = new ArrayList<Likes>();
		likesRepository.findAll().forEach(e -> likesList.add(e));
		
		return likesList;
	}
	
	
}
