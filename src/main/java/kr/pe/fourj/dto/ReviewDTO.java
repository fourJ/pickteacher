package kr.pe.fourj.dto;

import java.util.Date;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;
import lombok.Data;

public class ReviewDTO {
	
	@Data
	public static class Create {
		private Student studentIdx;
		private Course courseIdx;
		private String content;
		private Date dateTime;
		private Integer star;
	}
	
	@Data
	public static class Update {
		private Long idx;
		private String content;
		private Date dateTime;
		private Integer star;
	}
	
	@Data
	public static class Delete {
		private Long idx;
	}
	
	@Data
	public static class Get {
		private Long idx;
		private Student studentIdx;
		private Course courseIdx;
		private String content;
		private Date dateTime;
		private Integer star;
	}
}
