package kr.pe.fourj.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(name="review_seq", sequenceName="review_seq", initialValue=1, allocationSize=1)
public class Review {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="review_seq")
	private Long idx;
	
	@NonNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="student_idx")
	private Student studentIdx;
	
	@NonNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_idx")
	private Course courseIdx;
	
	@NonNull
	private String content;
	
	@NonNull
	@Column(name="date_time")
	private Date dateTime;
	
	@NonNull
	private Integer star;
	
}
