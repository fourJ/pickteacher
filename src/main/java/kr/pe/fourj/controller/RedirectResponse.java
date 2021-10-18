package kr.pe.fourj.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectResponse {
	
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

}
