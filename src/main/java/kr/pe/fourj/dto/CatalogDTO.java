package kr.pe.fourj.dto;

import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Student;
import lombok.Data;

public class CatalogDTO {
	
	@Data
	public static class Create {
		private Student studentIdx;
		private Course courseIdx;
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
	}
}
