package HoangLong.web_basic.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Post extends CreateAuditable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "title") 
	private String title;
	
	@Column(name = "description", columnDefinition = "TEXT") 
	private String description;
	
	@ElementCollection
	@CollectionTable(name = "post_images", joinColumns = @JoinColumn(name = "post_id"))
	private List<String> images;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category categogy;

}
