package kr.pe.fourj.domain;

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
@SequenceGenerator(name="likes_seq", sequenceName="likes_seq", initialValue=1, allocationSize=1)
public class Likes {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="likes_seq")
	private Long idx;
	
	@NonNull
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="student_idx")
	private Student studentIdx;
	
	@NonNull
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name="course_idx")
	private Course courseIdx;
	
}
