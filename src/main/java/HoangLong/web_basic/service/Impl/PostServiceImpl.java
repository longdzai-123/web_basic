package HoangLong.web_basic.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import HoangLong.web_basic.dao.CategoryDao;
import HoangLong.web_basic.dao.PostDao;
import HoangLong.web_basic.dto.PostDTO;
import HoangLong.web_basic.dto.SearchPostDTO;
import HoangLong.web_basic.entity.Category;
import HoangLong.web_basic.entity.Post;
import HoangLong.web_basic.service.PostService;

@Service
@Transactional
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	CategoryDao categoryDao;

	@Override
	public void addPost(PostDTO postDTO) {
	   Post post = new ModelMapper().map(postDTO, Post.class);
   	   postDao.add(post); 	 
       postDTO.setId(post.getId());	
	}

	@Override
	public void updatePost(PostDTO postDTO) {
		Post post = postDao.getById(postDTO.getId());
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		if(postDTO.getImages() != null) {
			post.setImages(postDTO.getImages());
		}
		Category category = categoryDao.getById(postDTO.getCategogyId());
		post.setCategogy(category);
		
		postDao.add(post);
	}

	@Override
	public void deletePost(Long id) {
		Post post = postDao.getById(id);
		postDao.delete(post);
	}

	@Override
	public PostDTO getById(Long id) {
		Post post = postDao.getById(id);
		PostDTO postDTO = new ModelMapper().map(post, PostDTO.class);
		return postDTO;
	}

	@Override
	public List<PostDTO> find(SearchPostDTO searchPostDTO) {
		List<Post> list = postDao.find(searchPostDTO);
		List<PostDTO> postDTOs = new ArrayList<>();
		for (Post post : list) {
		   PostDTO postDTO = new ModelMapper().map(post, PostDTO.class);
		   postDTOs.add(postDTO);
		}
		return postDTOs;
	}

	@Override
	public long count(SearchPostDTO searchPostDTO) {
		return postDao.count(searchPostDTO);
	}

	@Override
	public long countTotal(SearchPostDTO searchPostDTO) {
		return postDao.countTotal(searchPostDTO);
	}
     
}
