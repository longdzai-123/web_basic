package HoangLong.web_basic.service;

import java.util.List;

import HoangLong.web_basic.dto.CommentDTO;
import HoangLong.web_basic.dto.SearchCommentDTO;

public interface CommentService {

	void addComment(CommentDTO commentDTO);

	void updateComment(CommentDTO commentDTO);

	void deleteComment(Long id);

	CommentDTO getById(Long id);

	List<CommentDTO> find(SearchCommentDTO searchCommentDTO);

	long count(SearchCommentDTO searchCommentDTO);

	long countTotal(SearchCommentDTO searchCommentDTO);
}
