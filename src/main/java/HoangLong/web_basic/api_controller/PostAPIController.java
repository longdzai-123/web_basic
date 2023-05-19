package HoangLong.web_basic.api_controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import HoangLong.web_basic.dto.PostDTO;
import HoangLong.web_basic.dto.ResponseDTO;
import HoangLong.web_basic.dto.SearchPostDTO;
import HoangLong.web_basic.service.PostService;
import HoangLong.web_basic.utils.FileStore;
@RestController
@RequestMapping("/api")
public class PostAPIController {
	@Autowired
	PostService postService;
	
	@PostMapping("/member/post/add")
	public PostDTO add(@ModelAttribute PostDTO postDTO,HttpServletRequest request ) {
		postDTO.setImages(FileStore.getFilePaths(postDTO.getImageFile(), "post-"));
		postService.addPost(postDTO);
		return postDTO;
	}
	
	@PutMapping("/admin/post/update")
	public void update(@ModelAttribute PostDTO postDTO) {
		postDTO.setImages(FileStore.getFilePaths(postDTO.getImageFile(), "post-"));
		postService.updatePost(postDTO);
	}
	
	@DeleteMapping("/admin/post/delete")
	public void delete(@RequestParam("id") Long id) {
		postService.deletePost(id);
	}
	
	@DeleteMapping("/admin/post/delete-multi")
	public void deleteMulti(@RequestParam("ids") List<Long> ids) {
		for (Long id : ids) {
			postService.deletePost(id);
		}
	}
	
	@GetMapping("/post/{id}")
	public PostDTO get(@PathVariable("id") Long id) {
		return postService.getById(id);
	}
	
	@PostMapping("/post/search")
	public ResponseDTO<PostDTO> find(@RequestBody SearchPostDTO searchPostDTO){
		ResponseDTO<PostDTO> responseDTO = new ResponseDTO<PostDTO>();
		responseDTO.setRecordsFiltered(postService.count(searchPostDTO));
		responseDTO.setRecordsTotal(postService.countTotal(searchPostDTO));
		responseDTO.setData(postService.find(searchPostDTO));
		return responseDTO;
 	}
	
	
	
	
	
}
