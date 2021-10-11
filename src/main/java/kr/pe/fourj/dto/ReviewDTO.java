package kr.pe.fourj.dto;

import java.util.Date;
import java.util.List;

import kr.pe.fourj.domain.Cart;
import kr.pe.fourj.domain.Catalog;
import kr.pe.fourj.domain.Course;
import kr.pe.fourj.domain.Likes;
import kr.pe.fourj.domain.Review;
import kr.pe.fourj.domain.Student;
import lombok.Data;

public class ReviewDTO {
	
	@Data
	public static class Create {
		private Long studentIdx;
		private Long courseIdx;
		private String content;
		private Date dateTime;
		private Integer star;
	}
	
	@Data
	public static class Update {
		private Long idx;
		private String content;
		private Date dateTime;
		private Integer star;
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
		private String content;
		private Date dateTime;
		private Integer star;
		
		private List<Catalog> catalogList;
		private List<Cart> cartList;
		private List<Likes> likesList;
		private List<Review> reviewList;
	}
}
