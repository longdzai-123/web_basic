package HoangLong.web_basic.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "comment")
public class Comment extends CreateAuditable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String content;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private Date createDate;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post; 
	
	
	
	
	

}
