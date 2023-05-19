package HoangLong.web_basic.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchCommentDTO extends SearchDTO{
	private Long userId;
	private Long postId;
}
