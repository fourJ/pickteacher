package kr.pe.fourj.dto;

import lombok.Data;

public class CartDTO {
	
	@Data
	public static class Create {
		private Long studentIdx;
		private Long courseIdx;
	}
	
	@Data
	public static class Delete {
		private Long courseIdx;
	}
	
	@Data
	public static class Get {
		private Long studentIdx;
		private Long courseIdx;
	}
}
