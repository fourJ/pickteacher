package kr.pe.fourj.dto;

import lombok.Data;
import lombok.NonNull;

public class TeacherDTO {
	
	@Data
	public static class Create {
		private String id;
		private String pw;
		private String name;
		private Integer gender;
		private String address;
		private String phone;
		private String career;		
		private String major;
		private String school;
	}
	
	@Data
	public static class Update {
		private String address;
		private String phone;
	}
	
	@Data
	public static class Get {
		private Long idx;
		private String name;
		private Integer gender;
		private String school;
		private String major;
	}
	
	@Data
	public static class Login {
		@NonNull
		private String id;
		
		@NonNull
		private String pw;
	}

}
