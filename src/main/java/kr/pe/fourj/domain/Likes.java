package kr.pe.fourj.domain;

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
@SequenceGenerator(name="likes_seq", sequenceName="likes_seq", initialValue=1, allocationSize=1)
public class Likes {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="likes_seq")
	private Long idx;
	
	@NonNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="student_idx")
	private Student studentIdx;
	
	@NonNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_idx")
	private Course courseIdx;
	
}
