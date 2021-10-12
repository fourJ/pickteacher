package kr.pe.fourj.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@SequenceGenerator(name="cart_seq", sequenceName="cart_seq", initialValue=1, allocationSize=1)
public class Cart {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cart_seq")
	private Long idx;
	
	@NonNull
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="student_idx")
	private Student studentIdx;
	
	@NonNull
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="course_idx")
	private Course courseIdx;
	
}
