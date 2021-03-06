package kr.pe.fourj.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(name="course_seq", sequenceName="course_seq", initialValue=1, allocationSize=1)
public class Course {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="course_seq")
	private Long idx;
	
	@NonNull
	@JsonManagedReference	
	@ManyToOne
	@JoinColumn(name="teacher_idx")
	private Teacher teacherIdx;
	
	@NonNull
	private String title;
	
	@NonNull
	private String subject;
	
	@NonNull
	private String schedule;
	
	@NonNull
	private String type;
	
	@Column(name="open_date")
	@NonNull
	private LocalDate openDate;
	
	@Column(name="close_date")
	@NonNull
	private LocalDate closeDate;
	
	@NonNull
	private String status;
	
	@NonNull
	@Column(name="head_count")
	private Integer headCount;
	
	@NonNull
	private Integer tuition;
	
	@NonNull
	private String target;
	
	@OneToMany(mappedBy="courseIdx", cascade=CascadeType.ALL)
	@JsonBackReference
	private List<Review> reviewList;
	
	@OneToMany(mappedBy="courseIdx", cascade=CascadeType.ALL)
	@JsonBackReference
	private List<Catalog> catalogList;
	
	@OneToMany(mappedBy="courseIdx", cascade=CascadeType.ALL)
	@JsonBackReference
	private List<Likes> likesList;
	
	@OneToMany(mappedBy="courseIdx", cascade=CascadeType.ALL)
	@JsonBackReference
	private List<Cart> cartList;

}
