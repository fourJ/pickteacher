package kr.pe.fourj.controller;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import kr.pe.fourj.domain.Catalog;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.domain.Teacher;
import kr.pe.fourj.dto.CourseDTO;
import kr.pe.fourj.dto.StudentDTO;
import kr.pe.fourj.dto.TeacherDTO;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.service.CartService;
import kr.pe.fourj.service.CatalogService;
import kr.pe.fourj.service.CourseService;
import kr.pe.fourj.service.StudentService;
import kr.pe.fourj.service.TeacherService;

@RestController
public class RedirectResponse {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private TeacherService teacherService;
	
	/**
	 * Student
	 */
	//학생 저장
	@PostMapping("/student")
	public RedirectView saveStudent(StudentDTO.Create dto) throws NotFoundException {
		System.out.println("-- 학생 저장시도 --");

		LocalDate now = LocalDate.now();
		LocalDate birth = dto.getBirth();

		Period period = Period.between(birth, now);
		int age = period.getYears() + 1;


		if (studentService.findStudentById(dto.getId()) == null) {
			try {
				studentService.saveStudent(new Student(dto.getId(), dto.getPw(), dto.getName(), dto.getBirth(),
						age, dto.getNickName(), dto.getGender(), dto.getAddress(), dto.getPhone()));
			} catch (ArgumentNullException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("이미 존재하는 회원id입니다.");
		}

		return new RedirectView("index.html");
	}
	
	//학생 수정
	@PutMapping("/student")
	public RedirectView updateStudent(StudentDTO.Update dto) {
		System.out.println("-- 학생 수정 시도 --");

		try {
			studentService.updateStudent(dto);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return new RedirectView("mypage/mypage_student.html");
	}

	/**
	 *	Teacher 
	 */
	//선생님 저장
	@PostMapping("/teacher")
	public RedirectView saveTeacher(TeacherDTO.Create dto) {
		System.out.println("-- 선생님 저장 시도 --");
		
		LocalDate date = LocalDate.now();
		if(teacherService.findTeacherById(dto.getId()) == null) {
			try {
				teacherService.saveTeacher(new Teacher(dto.getId(),dto.getPw(), 
																dto.getName(), dto.getGender(), 
																dto.getAddress(), dto.getPhone(), 
																dto.getCareer(), dto.getMajor(), 
																dto.getSchool(), date));
			} catch (ArgumentNullException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("이미 존재하는 회원id입니다.");
		}
		return new RedirectView("");
	}
	
	//선생님 수정
	@PutMapping("/teacher/update")
	public RedirectView updateTeacher(TeacherDTO.Update dto) {
		System.out.println("-- 선생님 수정 시도 --");
		
		try {
			teacherService.updateTeacher(dto);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}		
		return new RedirectView("mypage/mypage_teacher.html");
	}
		
	//선생님 찾기에서 상세페이지로 이동
	@GetMapping("getTeacherDetail/{idx}")
	public RedirectView getTeacherDetail(@PathVariable Long idx, RedirectAttributes attr) {
		attr.addAttribute("teacherIdx", idx);
		return new RedirectView("/detail/teacher.html");
	}
	
	/**
	 *	Review 
	 */
	// 내가 쓴 글에서 상세페이지로 이동
	@GetMapping("getReviewDetail/{idx}")
	public RedirectView getReviewDetail(@PathVariable Long idx, RedirectAttributes attr) {
		attr.addAttribute("reviewIdx", idx);
		return new RedirectView("/reviewcommunity/reviewupdatepage.html");
	}

	/**
	 * Course
	 */
	//강의 수정
	@PutMapping("/course")
	public RedirectView updateCourse(CourseDTO.Update dto) {
		System.out.println("-- 강의 수정 시도 --");

		try {
			Course course = courseService.findOne(dto.getIdx());
			courseService.updateCourse(course.getIdx(), dto);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return new RedirectView("mypage/teacher_courseList.html");
	}
	
	//강의 찾기에서 상세페이지로 이동
	@GetMapping("getCourseDetail/{idx}")
	public RedirectView getCourseDetail(@PathVariable Long idx, RedirectAttributes attr) {
		attr.addAttribute("courseIdx", idx);
		return new RedirectView("/detail/course.html");
	}

	//강의 수정 페이지로 이동
	@GetMapping("getCourseUpdate/{idx}")
	public RedirectView getCourseUpdate(@PathVariable Long idx, RedirectAttributes attr) {
		attr.addAttribute("courseIdx", idx);
		return new RedirectView("/mypage/teacher_courseupdate.html");
	}

	//학생리스트로 이동
	@GetMapping("getCourseStudent/{idx}")
	public RedirectView getCourseStudent(@PathVariable Long idx, RedirectAttributes attr) {
		attr.addAttribute("courseIdx", idx);
		return new RedirectView("/mypage/teacher_coursestudent.html");
	}
	
	/**
	 * Cart
	 */
	//수강 신청시 수강 내역으로 연동
	@GetMapping("sendCatalog/{courseIdx}/cart/{studentIdx}")
	public RedirectView sendCatalog(@PathVariable Long courseIdx, @PathVariable Long studentIdx) {
		System.out.println("-- 수강 신청시 수강 내역으로 이동 시도 --");

		try {
			Student student = studentService.findOne(studentIdx);
			Course course = courseService.findOne(courseIdx);		
			LocalDate date = LocalDate.now();

			if(courseService.checkStatus(course) && course.getHeadCount() > course.getCatalogList().size()) {
				if(catalogService.isNotAlreadyCatalog(student, course)) {
					try {
						catalogService.saveCatalog(new Catalog(student, course, date));
						cartService.deleteCart(student, course);
					}
					catch (ArgumentNullException e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("동일한 강의를 이미 수강하고 있습니다.");
					try {
						cartService.deleteCart(student, course);
					} catch (ArgumentNullException e) {
						e.printStackTrace();
					}
				}			
			} else {
				System.out.println("마감일이 지났거나 정원이 초과했습니다.");
				try {
					cartService.deleteCart(student, course);
				} catch (ArgumentNullException e) {
					e.printStackTrace();
				}
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return new RedirectView("/mypage/student_catalog.html");
	}

}
