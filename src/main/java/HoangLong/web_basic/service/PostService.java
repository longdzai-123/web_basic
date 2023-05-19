package HoangLong.web_basic.service;

import java.util.List;

import HoangLong.web_basic.dto.PostDTO;
import HoangLong.web_basic.dto.SearchPostDTO;

public interface PostService {
	void addPost(PostDTO postDTO);
    
    void updatePost(PostDTO postDTO);
    
    void deletePost(Long id);
    
    PostDTO getById(Long id);
    
    List<PostDTO> find(SearchPostDTO searchPostDTO);
    
    long count(SearchPostDTO searchPostDTO);
    
    long countTotal(SearchPostDTO searchPostDTO);
}
