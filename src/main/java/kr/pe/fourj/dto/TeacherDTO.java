package kr.pe.fourj.dto;

import lombok.Data;

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
		private Long idx;
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
		private String name;
<<<<<<< HEAD
		private Integer birth;
=======
>>>>>>> 875b0097c0e45a70869806f055bd100b81369f8e
		private Integer gender;
		private String school;
		private String major;
	}

}
