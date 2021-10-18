package kr.pe.fourj.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NonNull;

public class StudentDTO {
	
	@Data
	public static class Create {
		private String id;
		private String pw;
		private String name;
		
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private LocalDate birth;
		
		private String nickName;
		private String gender;
		private String address;
		private String phone;
	}
	
	@Data
	public static class Update {
		private Long idx;
		private String pw;
		private String nickName;
		private String address;
		private String phone;
	}
	
	@Data
	public static class Delete {
		private Long idx;
	}
	
	@Data
	public static class Get {
		private Long idx;
		private Long courseIdx;
	}
	
	@Data
	public static class Login {
		@NonNull
		private String id;
		
		@NonNull
		private String pw;
	}

}
