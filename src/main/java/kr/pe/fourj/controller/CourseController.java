package kr.pe.fourj.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Teacher;
import kr.pe.fourj.dto.CourseDTO;
import kr.pe.fourj.dto.ResponseDTO;
import kr.pe.fourj.dto.TeacherDTO;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.service.CourseService;
import kr.pe.fourj.service.TeacherService;

@RestController
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	@Autowired
	private TeacherService teacherService;
	
	//강의 저장
	@PostMapping("/course")
	public ResponseDTO.Create saveCourse(HttpServletRequest request, @RequestBody CourseDTO.Create dto) {
		System.out.println("-- 강의 저장 시도 --");
		
		boolean result = false;
		Long saveId = null;
		if(request.getSession().getAttribute("teacher") != null) {
			Object object = request.getSession().getAttribute("teacher");
			Teacher entity = (Teacher)object;

			String status = courseService.calculateStatus(dto.getOpenDate(), dto.getCloseDate());
			
			try {
				Teacher teacher = teacherService.findOne(entity.getIdx());
				
				try {
					saveId = courseService.saveCourse(new Course(teacher, 
																 dto.getTitle(), dto.getSubject(), 
																 dto.getSchedule(), dto.getType(), 
																 dto.getOpenDate(), dto.getCloseDate(), 
																 status, dto.getHeadCount(), 
																 dto.getTuition(), dto.getTarget()));
					result = true;
				} catch (ArgumentNullException e) {
					e.printStackTrace();
				}
			} catch (NotFoundException e1) {
				e1.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}
		
		return new ResponseDTO.Create(saveId, result);
	}
	
	//강의 찾기에서 상세페이지로 이동
	@GetMapping("getCourseDetail/{idx}")
	public RedirectView getCourseDetail(@PathVariable Long idx, RedirectAttributes attr) {
		attr.addAttribute("courseIdx", idx);
		return new RedirectView("/detail/course.html");
	}

	//강의 수정
	@PutMapping("/course")
	public ResponseDTO.Update updateCourse(HttpServletRequest request, @RequestBody CourseDTO.Update dto) {
		System.out.println("-- 강의 수정 시도 --");
		
		boolean result = false;
		if(request.getSession().getAttribute("teacher") != null) {
			Object object = request.getSession().getAttribute("teacher");
			Teacher entity = (Teacher)object;
			
			try {
				Teacher teacher = teacherService.findOne(entity.getIdx());
				Course course = courseService.findOne(dto.getIdx());
				
				if(course.getTeacherIdx().getIdx() == teacher.getIdx()) {
					courseService.updateCourse(course.getIdx(), dto);
					result = true;
				}else {
					System.out.println("본인의 강의만 수정할 수 있습니다.");
				}
			} catch (NotFoundException e1) {
				e1.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}

		return new ResponseDTO.Update(result);
	}

	//강의 삭제
	@DeleteMapping("/course")
	public ResponseDTO.Delete deleteCourse(HttpServletRequest request, @RequestBody CourseDTO.Delete dto) {
		System.out.println("-- 강의 삭제 시도 --");
		
		boolean result = false;
		if(request.getSession().getAttribute("teacher") != null) {
			Object object = request.getSession().getAttribute("teacher");
			Teacher entity = (Teacher)object;
			
			try {
				Teacher teacher = teacherService.findOne(entity.getIdx());
				Course course = courseService.findOne(dto.getIdx());
				
				if(course.getTeacherIdx().getIdx() == teacher.getIdx()) {
					courseService.deleteCourse(course.getIdx(), dto);
					result = true;
				}else {
					System.out.println("본인의 강의만 삭제할 수 있습니다.");
				}
			} catch (NotFoundException e1) {
				e1.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}
		
		return new ResponseDTO.Delete(result);
	}
	
	//강의 단일 검색
	@GetMapping("/course")
	public ResponseDTO.CourseResponse findOne(CourseDTO.Get dto) {
		System.out.println("-- 강의 단일 검색 시도 --");
		
		boolean result = false;
		Course course = null;
		try {
			course = courseService.findOne(dto.getIdx());
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		
		return new ResponseDTO.CourseResponse(result, course);
	}

	//강의 리스트 전체 검색
	@GetMapping("/courseall")
	public ResponseDTO.CourseListResponse findAll() {
		System.out.println("-- 강의 리스트 전체 검색 시도 --");
		
		List<Course> courseList = courseService.findAll();
		
		return new ResponseDTO.CourseListResponse(true, courseList);
	}
	
	//특정 강의 제목으로 강의 리스트 검색
	@GetMapping("/course/titlecontaining")
	public ResponseDTO.CourseListResponse findAllByTitleContaining(CourseDTO.Get dto) {
		System.out.println("-- 강의 제목이 " + dto.getTitle() + "인 강의 리스트 검색 시도 --");
		
		List<Course> courseList = courseService.findAllByTitleContaining(dto);

		return new ResponseDTO.CourseListResponse(true, courseList);
	}

	//특정 선생님(자신)의 강의 리스트 검색
	@GetMapping("/course/myidx")
	public ResponseDTO.CourseListResponse findAllByMyIdx(HttpServletRequest request) {
		System.out.println("-- 나의 강의 리스트 검색 시도 --");
		
		boolean result = false;
		List<Course> courseList = null;
		if(request.getSession().getAttribute("teacher") != null) {
			Object object = request.getSession().getAttribute("teacher");
			Teacher entity = (Teacher)object;
			
			try {
				Teacher teacher = teacherService.findOne(entity.getIdx());
				courseList = teacher.getCourseList();
				result = true;
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("로그인 정보가 없습니다. 로그인이 필요한 기능입니다.");
		}
		
		return new ResponseDTO.CourseListResponse(result, courseList);
	}
	
	//특정 선생님의 강의 리스트 검색
	@GetMapping("/course/teacheridx")
	public ResponseDTO.CourseListResponse findAllByTeacherIdx(TeacherDTO.Get dto) {
		System.out.println("-- 특정 선생님의 강의 리스트 검색 시도 --");

		boolean result = false;
		List<Course> courseList = null;
		try {
			Teacher teacher = teacherService.findOne(dto.getIdx());
			courseList = teacher.getCourseList();
			result = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return new ResponseDTO.CourseListResponse(result, courseList);
	}

	//과목으로 강의 리스트 검색
	@GetMapping("/course/subject")
	public ResponseDTO.CourseListResponse findAllBySubject(CourseDTO.Get dto) {
		System.out.println("-- 특정 과목 강의 리스트 검색 시도 --");

		List<Course> courseList = courseService.findAllBySubject(dto);

		return new ResponseDTO.CourseListResponse(true, courseList);
	}
	
	//타겟(대상학년)으로 강의 리스트 검색
	@GetMapping("/course/target")
	public ResponseDTO.CourseListResponse findAllByTarget(CourseDTO.Get dto) {
		System.out.println("-- 특정 타겟으로 강의 리스트 검색 시도 --");

		List<Course> courseList = courseService.findAllByTarget(dto);

		return new ResponseDTO.CourseListResponse(true, courseList);
	}
	
	//스케줄(요일)로 강의 리스트 검색
	@GetMapping("/course/schedule")
	public ResponseDTO.CourseListResponse findAllBySchedule(CourseDTO.Get dto) {
		System.out.println("-- 특정 타겟으로 강의 리스트 검색 시도 --");

		List<Course> courseList = courseService.findAllBySchedule(dto);

		return new ResponseDTO.CourseListResponse(true, courseList);
	}
	
	//타입(진행방식)으로 강의 리스트 검색
	@GetMapping("/course/type")
	public ResponseDTO.CourseListResponse findAllByType(CourseDTO.Get dto) {
		System.out.println("-- 특정 타겟으로 강의 리스트 검색 시도 --");

		List<Course> courseList = courseService.findAllByType(dto);

		return new ResponseDTO.CourseListResponse(true, courseList);
	}
	
	//가격으로 강의 리스트 검색(입력된 금액 이하 강의 찾기)
	@GetMapping("/course/tuition")
	public ResponseDTO.CourseListResponse findAllByTuition(CourseDTO.Get dto) {
		System.out.println("-- 특정 타겟으로 강의 리스트 검색 시도 --");

		List<Course> courseList = courseService.findAllByTuition(dto);

		return new ResponseDTO.CourseListResponse(true, courseList);
	}
}
