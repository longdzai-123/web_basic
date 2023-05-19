package HoangLong.web_basic.dao;

import java.util.List;

import HoangLong.web_basic.dto.SearchPostDTO;
import HoangLong.web_basic.entity.Post;

public interface PostDao {
	
	void add(Post post);
	
    void delete(Post post);
	
	void update(Post post);
	
	Post getById(Long id);
	
	List<Post> find(SearchPostDTO searchPostDTO);
	
	long count(SearchPostDTO searchPostDTO);
	
	long countTotal(SearchPostDTO searchPostDTO);

}
