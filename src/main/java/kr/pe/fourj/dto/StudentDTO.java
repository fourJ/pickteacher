package kr.pe.fourj.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NonNull;

public class StudentDTO {
	
	@Data
	public static class Create {
		private String id;
		private String pw;
		private String name;
		private LocalDate birth;
		private String nickName;
		private String gender;
		private String address;
		private String phone;
	}
	
	@Data
	public static class Update {
		private String nickName;
		private String address;
		private String phone;
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
