package kr.pe.fourj.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

public class CourseDTO {
	
	@Data
	public static class Create {
		private Long teacherIdx;
		private String title;
		private String subject;
		private String schedule;
		private String type;
		
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate openDate;
	
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate closeDate;
		private Integer headCount;
		private Integer tuition;
		private String target;
	}
	
	@Data
	public static class Update {
		private Long idx;
		private String title;
		private String schedule;
		
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate openDate;
		
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate closeDate;
		
		private Integer headCount;
		private String type;
		private Integer tuition;
		private String target;
	}
	
	@Data
	public static class Delete {
		private Long idx;
	}
	
	@Data
	public static class Get {
		private Long idx;
		private Long studentIdx;
		private Long teacherIdx;
		private String title;
		private String subject;
		private String target;
		private String schedule;
		private String type;
		private Integer tuition;
	}
}
