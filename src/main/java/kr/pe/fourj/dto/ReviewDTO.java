package kr.pe.fourj.dto;

import java.time.LocalDateTime;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;
import lombok.Data;

@Data
public class ReviewDTO {
	
	private Long idx;
	private Student studentIdx;
	private Course courseIdx;
	private String content;
	private LocalDateTime dateTime;
	private Integer star;
	
	@Data
	public static class Create {
		private Long studentIdx;
		private Long courseIdx;
		private String content;
		private Integer star;
	}
	
	@Data
	public static class Update {
		private Long idx;
		private String content;
		private Integer star;
	}
	
	@Data
	public static class Delete {
		private Long idx;
	}
	
	@Data
	public static class Get {
		private Long idx;
		private Long studentIdx;
		private Long courseIdx;
	}
	
}
