package HoangLong.web_basic.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import HoangLong.web_basic.dao.CategoryDao;
import HoangLong.web_basic.dto.SearchCategoryDTO;
import HoangLong.web_basic.entity.Category;

@Repository
@Transactional
public class CategoryDaoImpl extends JPARepository<Category> implements CategoryDao {
	
	@Override
	public Category getById(Long id) {
		Category categogy = entityManager.find(Category.class, id);
		return categogy;
	}

	@Override
	public List<Category> find(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Category> query = builder.createQuery(Category.class);
		Root<Category> root = query.from(Category.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(StringUtils.isNotBlank(searchCategoryDTO.getKeyword())) {
			Predicate predicate = builder.like(builder.lower(root.get("name")), "%" +searchCategoryDTO.getKeyword() +"%");
			predicates.add(predicate);
		}
		query.where(predicates.toArray(new Predicate[] {}));
		
		// order 
		if(StringUtils.equals(searchCategoryDTO.getSortBy().getData(),"id" )) {
			if(searchCategoryDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("id")));
			}else {
				query.orderBy(builder.desc(root.get("id")));
			}
			
		}else if(StringUtils.equals(searchCategoryDTO.getSortBy().getData(), "name")) {
			if(searchCategoryDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("name")));
			}else {
				query.orderBy(builder.desc(root.get("name")));
			}
		}
		TypedQuery<Category> typedQuery = entityManager.createQuery(query.select(root));
		if (searchCategoryDTO.getStart() != null) {
			typedQuery.setFirstResult((searchCategoryDTO.getStart()));
			typedQuery.setMaxResults(searchCategoryDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public long count(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Category> root = query.from(Category.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(StringUtils.isNotBlank(searchCategoryDTO.getKeyword())) {
			Predicate predicate = builder.like(builder.lower(root.get("name")), "%" +searchCategoryDTO.getKeyword() +"%");
			predicates.add(predicate);
		}
		query.where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public long countTotal(SearchCategoryDTO searchCategoryDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Category> root = query.from(Category.class);
		
		TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}
	
}
