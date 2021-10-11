package kr.pe.fourj.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@ManyToOne
	@JoinColumn(name="teacher_idx")
	private Teacher teacherIdx;
	
	@NonNull
	private String subject;
	
	@NonNull
	private String schedule;
	
	@NonNull
	private String type;
	
	@Column(name="open_date")
	private Date openDate;
	
	@Column(name="close_date")
	private Date closeDate;
	
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
	private List<Review> reviewList;
	
	@OneToMany(mappedBy="courseIdx", cascade=CascadeType.ALL)
	private List<Catalog> catalogList;
	
	@OneToMany(mappedBy="courseIdx", cascade=CascadeType.ALL)
	private List<Likes> likesList;

}
