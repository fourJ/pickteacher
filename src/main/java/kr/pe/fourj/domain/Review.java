package kr.pe.fourj.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

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
@SequenceGenerator(name="review_seq", sequenceName="review_seq", initialValue=1, allocationSize=1)
public class Review {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="review_seq")
	private Long idx;
	
	@NonNull
	@ManyToOne
	@JoinColumn(name="student_idx")
	@JsonManagedReference
	private Student studentIdx;
	
	@NonNull
	@ManyToOne
	@JoinColumn(name="course_idx")
	@JsonManagedReference
	private Course courseIdx;
	
	@NonNull
	private String content;
	
	@NonNull
	@Column(name="date_time")
	private LocalDateTime dateTime;
	
	@NonNull
	private Integer star;
	
}
