package HoangLong.web_basic.dao;

import java.util.List;

import HoangLong.web_basic.dto.SearchCommentDTO;
import HoangLong.web_basic.entity.Comment;

public interface CommentDao {
      void add(Comment comment);
      
      void update(Comment comment);
      
      void delete(Comment comment);
      
      Comment get(Long id);
      
      List<Comment> find(SearchCommentDTO searchCommentDTO);
      
      Long count(SearchCommentDTO searchCommentDTO);

  	  Long countTotal(SearchCommentDTO searchCommentDTO);

      
}
