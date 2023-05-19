package HoangLong.web_basic.api_controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import HoangLong.web_basic.dto.CommentDTO;
import HoangLong.web_basic.dto.ResponseDTO;
import HoangLong.web_basic.dto.SearchCommentDTO;
import HoangLong.web_basic.dto.UserPrincipal;
import HoangLong.web_basic.service.CommentService;
import HoangLong.web_basic.utils.RoleEnum;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api")
public class CommentAPIController {
	@Autowired
	CommentService commentService;
	
	@PostMapping("/member/comment/add")
	public CommentDTO addComment(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
		UserPrincipal userPrincipal =(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (request.isUserInRole(RoleEnum.MEMBER.getRoleName())) {
			commentDTO.setUserId(userPrincipal.getId());
		}
		commentService.addComment(commentDTO);
		return commentDTO;
	}
	
	@DeleteMapping(value = "/admin/comment/delete")
	public void delete(@RequestParam(name = "id") Long id) {
		commentService.deleteComment(id);

	}

	@PostMapping(value = "/comment/search")
	public ResponseDTO<CommentDTO> find(@RequestBody SearchCommentDTO searchCommentDTO) {
		ResponseDTO<CommentDTO> responseDTO = new ResponseDTO<CommentDTO>();
		responseDTO.setData(commentService.find(searchCommentDTO));
		responseDTO.setRecordsFiltered(commentService.count(searchCommentDTO));
		responseDTO.setRecordsTotal(commentService.countTotal(searchCommentDTO));
		return responseDTO;
	}

}
