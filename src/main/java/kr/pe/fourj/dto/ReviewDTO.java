package kr.pe.fourj.dto;

import lombok.Data;

public class ReviewDTO {
	
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
