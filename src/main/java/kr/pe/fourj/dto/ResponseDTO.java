package kr.pe.fourj.dto;

import java.util.List;

import kr.pe.fourj.domain.Catalog;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Review;
import kr.pe.fourj.domain.Student;
import kr.pe.fourj.domain.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;

public class ResponseDTO {
	
	@Data
	@AllArgsConstructor
    public static class BaseResponse {
        Boolean success;
    }
	
	public static class Create extends BaseResponse {
		Long id;
		
		public Create(Long id, Boolean success) {
			super(success);
			this.id = id;
		}
	}
	
	public static class Update extends BaseResponse {
		public Update(Boolean success) {
			super(success);
		}
	}
	
	public static class Delete extends BaseResponse {
		public Delete(Boolean success) {
			super(success);
		}
	}
	
	@Data
	@AllArgsConstructor
	public static class CatalogResponse {
		Boolean success;
		private Catalog catalog;
	}
	
	@Data
	@AllArgsConstructor
	public static class CatalogListResponse {
		Boolean success;
		private List<Catalog> catalogList;
	}
	
	@Data
	@AllArgsConstructor
	public static class CourseResponse {
		Boolean success;
		private Course course;
	}
	
	@Data
	@AllArgsConstructor
	public static class CourseListResponse {
		Boolean success;
		private List<Course> courseList;
	}
	
	@Data
	@AllArgsConstructor
	public static class ReviewResponse {
		Boolean success;
		private Review review;
	}
	
	@Data
	@AllArgsConstructor
	public static class ReviewListResponse {
		Boolean success;
		private List<Review> reviewList;
	}
	
	@Data
	@AllArgsConstructor
	public static class StudentResponse {
		Boolean success;
		private Student student;
	}
	
	@Data
	@AllArgsConstructor
	public static class StudentListResponse {
		Boolean success;
		private List<Student> studentList;
	}
	
	@Data
	@AllArgsConstructor
	public static class TeacherResponse {
		Boolean success;
		private Teacher teacher;
	}
	
	@Data
	@AllArgsConstructor
	public static class TeacherListResponse {
		Boolean success;
		private List<Teacher> teacherList;
	}
}
