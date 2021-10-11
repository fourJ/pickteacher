package kr.pe.fourj.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@SequenceGenerator(name="teacher_seq", sequenceName="teacher_seq", initialValue=1, allocationSize=1)
public class Teacher {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="teacher_seq")
	private Long idx;

	@NonNull
	private String id;

	@NonNull
	private String pw;

	@NonNull
	private String name;
	
	@NonNull
	private Integer gender;
	
	@NonNull
	private String address;
	
	@NonNull
	private String phone;
	
	@NonNull
	private String career;
	
	@NonNull
	private String major;
	
	@NonNull
	private String school;
	
	@NonNull
	@Column(name="enroll_date")
	private Date enrollDate;

	@OneToMany(mappedBy="teacherIdx", cascade=CascadeType.ALL)
	private List<Course> courseList;
}
