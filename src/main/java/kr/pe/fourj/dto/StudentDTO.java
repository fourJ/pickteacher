package kr.pe.fourj.dto;

import java.util.Date;

import lombok.Data;

public class StudentDTO {
	
	@Data
	public static class Create {
		private String id;
		private String pw;
		private String name;
		private Date birth;
		private Integer age;
		private String nickName;
		private Integer gender;
		private String address;
		private String phone;
	}
	
	
	@Data
	public static class Update {
		private Long idx;
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

}
