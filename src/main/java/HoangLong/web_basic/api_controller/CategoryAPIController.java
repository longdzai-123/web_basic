package HoangLong.web_basic.api_controller;

import java.util.List;

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

import HoangLong.web_basic.dto.CategoryDTO;
import HoangLong.web_basic.dto.ResponseDTO;
import HoangLong.web_basic.dto.SearchCategoryDTO;
import HoangLong.web_basic.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryAPIController {
	@Autowired
	CategoryService categoryService;
    
	@PostMapping("/admin/category/add")
	public CategoryDTO add(@RequestBody CategoryDTO categoryDTO) {
		categoryService.addCategory(categoryDTO);
		return categoryDTO;
	}
	
	@PostMapping("/admin/category/add-form")
	public CategoryDTO addForm(@ModelAttribute CategoryDTO categoryDTO) {
		categoryService.addCategory(categoryDTO);
		return categoryDTO;
	}
	
	@DeleteMapping("/admin/category/delete")
	public void delete(@RequestParam("id") Long id) {
		categoryService.deleteCategory(id);
	}
	
	@DeleteMapping("/admin/category/delete-multi")
	public void deleteMulti(@RequestParam("ids") List<Long> ids){
		for (Long id : ids) {
			categoryService.deleteCategory(id);
		}
	}
	
	@PutMapping("/admin/category/update")
	public void update(@RequestBody CategoryDTO categoryDTO) {
		categoryService.updateCategory(categoryDTO);
	}
	
	@GetMapping("/category/{id}")
	public CategoryDTO get(@PathVariable("id") Long id) {
		return categoryService.getById(id);
	}
	
	@PostMapping("/category/search")
	public ResponseDTO<CategoryDTO> find(@RequestBody SearchCategoryDTO searchCategoryDTO){
		ResponseDTO<CategoryDTO> responseDTO = new ResponseDTO<CategoryDTO>();
		responseDTO.setRecordsFiltered(categoryService.count(searchCategoryDTO));
		responseDTO.setRecordsTotal(categoryService.countTotal(searchCategoryDTO));
		responseDTO.setData(categoryService.find(searchCategoryDTO));
		
		return responseDTO;
	}
	
	
}
