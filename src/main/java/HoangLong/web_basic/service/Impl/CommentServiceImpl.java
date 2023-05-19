package HoangLong.web_basic.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import HoangLong.web_basic.dao.CommentDao;
import HoangLong.web_basic.dao.PostDao;
import HoangLong.web_basic.dao.UserDao;
import HoangLong.web_basic.dto.CommentDTO;
import HoangLong.web_basic.dto.SearchCommentDTO;
import HoangLong.web_basic.entity.Comment;
import HoangLong.web_basic.entity.Post;
import HoangLong.web_basic.entity.User;
import HoangLong.web_basic.service.CommentService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	PostDao postDao;

	@Override
	public void addComment(CommentDTO commentDTO) {
		Comment comment = new ModelMapper().map(commentDTO, Comment.class);
		commentDao.add(comment);
	}

	@Override
	public void updateComment(CommentDTO commentDTO) {
		Comment comment = commentDao.get(commentDTO.getId());
		
		if(comment != null) {
			comment.setContent(commentDTO.getContent());
			comment.setCreateDate(new Date());
			
			User user = userDao.getById(commentDTO.getUserId());
			comment.setUser(user);
			
			Post post = postDao.getById(commentDTO.getPostId());
			comment.setPost(post);
			
			commentDao.update(comment);
		}
		
	}

	@Override
	public void deleteComment(Long id) {
		Comment comment = commentDao.get(id);
		commentDao.delete(comment);
	}

	@Override
	public CommentDTO getById(Long id) {
		Comment comment = commentDao.get(id);
		CommentDTO commentDTO = new ModelMapper().map(comment, CommentDTO.class);
		return commentDTO;
	}

	@Override
	public List<CommentDTO> find(SearchCommentDTO searchCommentDTO) {
		List<Comment> comments = commentDao.find(searchCommentDTO);
		List<CommentDTO> commentDTOs = new ArrayList<CommentDTO>();
		for (Comment comment : comments) {
			CommentDTO commentDTO = new ModelMapper().map(comment, CommentDTO.class);
			commentDTOs.add(commentDTO);
		}
		return commentDTOs;
	}

	@Override
	public long count(SearchCommentDTO searchCommentDTO) {
		
		return commentDao.count(searchCommentDTO);
	}

	@Override
	public long countTotal(SearchCommentDTO searchCommentDTO) {
		
		return commentDao.countTotal(searchCommentDTO);
	}
     
}
