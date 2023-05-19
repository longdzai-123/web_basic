package HoangLong.web_basic.dao;

import java.util.List;

import HoangLong.web_basic.dto.SearchCategoryDTO;
import HoangLong.web_basic.entity.Category;

public interface CategoryDao {
     void add(Category categogy);
     
     void update(Category categogy);
     
     void delete(Category categogy);
    
     Category getById(Long id);
     
     List<Category> find(SearchCategoryDTO searchCategoryDTO);
     
     long count(SearchCategoryDTO searchCategoryDTO);
     
     long countTotal(SearchCategoryDTO searchCategoryDTO); 
     
}
