package kr.pe.fourj.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import kr.pe.fourj.domain.Catalog;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.exception.Exception.ArgumentNullException;
import kr.pe.fourj.exception.Exception.NotFoundException;
import kr.pe.fourj.service.CartService;
import kr.pe.fourj.service.CatalogService;
import kr.pe.fourj.service.CourseService;
import kr.pe.fourj.service.StudentService;

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
	
	/**
	 *	Teacher 
	 */
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

			//course.getHeadCount() > catalogService.findAllByCourseIdx(course).size()
			if(courseService.checkStatus(course)) {
				try {
					boolean checkCart = cartService.isNotAlreadyCart(student, course);
					if(!checkCart) {
						catalogService.saveCatalog(new Catalog(student, course, date));
						cartService.deleteCart(student, course);
					}else {
						System.out.println("해당 강의가 장바구니에 없습니다.");
					}
				} catch (ArgumentNullException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("동일한 강의를 이미 수강신청 하셨거나, 마감일이 지났거나, 정원이 다 찼습니다.");
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		}

		return new RedirectView("/mypage/student_catalog.html");
	}

}
