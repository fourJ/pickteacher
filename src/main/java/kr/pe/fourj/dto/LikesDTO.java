package kr.pe.fourj.dto;

import lombok.Data;

public class LikesDTO {

	@Data
	public static class Create {
		private Long studentIdx;
		private Long courseIdx;
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
