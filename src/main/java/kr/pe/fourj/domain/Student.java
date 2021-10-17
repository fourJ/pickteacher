package kr.pe.fourj.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@SequenceGenerator(name="student_seq", sequenceName="student_seq", initialValue=1, allocationSize=1)
public class Student {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="student_seq")
	private Long idx;
	
	@NonNull
	private String id;
	
	@NonNull
	private String pw;
	
	@NonNull
	private String name;
	
	@NonNull
	private LocalDate birth;
	

	@NonNull
	private Integer age;
	
	@NonNull
	@Column(name="nick_name")
	private String nickName;
	
	@NonNull
	private String gender;
	
	@NonNull
	private String address;
	
	@NonNull
	private String phone;
	
	@OneToMany(mappedBy="studentIdx", cascade=CascadeType.ALL)
	@JsonBackReference
	private List<Catalog> catalogList;
	
	@OneToMany(mappedBy="studentIdx", cascade=CascadeType.ALL)
	@JsonBackReference
	private List<Cart> cartList;
	
	@OneToMany(mappedBy="studentIdx", cascade=CascadeType.ALL)
	@JsonBackReference
	private List<Likes> likesList;
	
	@OneToMany(mappedBy="studentIdx", cascade=CascadeType.ALL)
	@JsonBackReference
	private List<Review> reviewList;

	
}
