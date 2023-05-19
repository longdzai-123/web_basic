package HoangLong.web_basic.dto;

import lombok.Data;

@Data
public class CommentDTO {
	private Long id;
	
	private String content;

	private Long userId;
	
	private String createDate;
	
	private Long postId; 
}
