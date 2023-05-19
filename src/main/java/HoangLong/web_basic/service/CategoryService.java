package HoangLong.web_basic.service;

import java.util.List;

import HoangLong.web_basic.dto.CategoryDTO;
import HoangLong.web_basic.dto.SearchCategoryDTO;

public interface CategoryService {
	
     void addCategory(CategoryDTO categoryDTO);
     
     void updateCategory(CategoryDTO categoryDTO);
     
     void deleteCategory(Long id);
     
     CategoryDTO getById(Long id);
     
     List<CategoryDTO> find(SearchCategoryDTO searchCategoryDTO);
     
     long count(SearchCategoryDTO searchCategoryDTO);
     
     long countTotal(SearchCategoryDTO searchCategoryDTO);
     
}
