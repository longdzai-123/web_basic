package HoangLong.web_basic.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import HoangLong.web_basic.dao.CategoryDao;
import HoangLong.web_basic.dto.CategoryDTO;
import HoangLong.web_basic.dto.SearchCategoryDTO;
import HoangLong.web_basic.entity.Category;
import HoangLong.web_basic.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDao categoryDao;
	
     @Override
     public void addCategory(CategoryDTO categoryDTO) {
    	 Category categogy = new ModelMapper().map(categoryDTO, Category.class);
    	 categoryDao.add(categogy);
    	 
    	 categoryDTO.setId(categogy.getId());
     }

	@Override
	public void updateCategory(CategoryDTO categoryDTO) {
		Category categogy = categoryDao.getById(categoryDTO.getId());
		if(categogy != null) {
			categogy.setName(categoryDTO.getName());
			categoryDao.update(categogy);
		}
	}

	@Override
	public void deleteCategory(Long id) {
		Category categogy = categoryDao.getById(id);
		if(categogy != null) {
			categoryDao.delete(categogy);
		}
	}

	@Override
	public CategoryDTO getById(Long id) {
		Category category = categoryDao.getById(id);
		CategoryDTO categoryDTO = new ModelMapper().map(category, CategoryDTO.class);
		return categoryDTO;
	}

	@Override
	public List<CategoryDTO> find(SearchCategoryDTO searchCategoryDTO) {
		List<Category> list = categoryDao.find(searchCategoryDTO);
		List<CategoryDTO> listDto = new ArrayList<>();
		for (Category categogy : list) {
			CategoryDTO categoryDTO = new ModelMapper().map(categogy, CategoryDTO.class);
			listDto.add(categoryDTO);
		}	
		return listDto;
	}

	@Override
	public long count(SearchCategoryDTO searchCategoryDTO) {
		
		return categoryDao.count(searchCategoryDTO);
	}

	@Override
	public long countTotal(SearchCategoryDTO searchCategoryDTO) {
		
		return categoryDao.countTotal(searchCategoryDTO);
	}
}
