package HoangLong.web_basic.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import HoangLong.web_basic.dao.PostDao;
import HoangLong.web_basic.dto.SearchPostDTO;
import HoangLong.web_basic.entity.Category;
import HoangLong.web_basic.entity.Post;
import HoangLong.web_basic.entity.User;

@Repository
@Transactional
public class PostDaoImpl extends JPARepository<Post> implements PostDao {
	
	 @Override
     public Post getById(Long id) {
    	 Post post = entityManager.find(Post.class, id);
    	 return post;
     }

	@Override
	public List<Post> find(SearchPostDTO searchPostDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Post> query = builder.createQuery(Post.class);
		Root<Post> root = query.from(Post.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(StringUtils.isNotBlank(searchPostDTO.getKeyword())) {
			Predicate predicate = builder.like(builder.lower(root.get("title")), "%"+searchPostDTO.getKeyword().toLowerCase()+"%");
			predicates.add(predicate);
		}
		if(searchPostDTO.getCategoryId() != null) {
			Join<Post, Category> category = root.join("category");
			Predicate predicate = builder.equal(category.get("id"), searchPostDTO.getCategoryId());
			predicates.add(predicate);
		}
		
		if (searchPostDTO.getCreatedById() != null) {
			Join<Post, User> user = root.join("createdBy");
			Predicate predicate = builder.equal(user.get("id"), searchPostDTO.getCreatedById());
			predicates.add(predicate);
		}

		query.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchPostDTO.getSortBy().getData(), "id")) {
			if (searchPostDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("id")));
			} else {
				query.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchPostDTO.getSortBy().getData(), "name")) {
			if (searchPostDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("name")));
			} else {
				query.orderBy(builder.desc(root.get("name")));
			}
		}

		TypedQuery<Post> typedQuery = entityManager.createQuery(query.select(root));
		if (searchPostDTO.getStart() != null) {
			typedQuery.setFirstResult((searchPostDTO.getStart()));
			typedQuery.setMaxResults(searchPostDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public long count(SearchPostDTO searchPostDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Post> root = query.from(Post.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchPostDTO.getKeyword())) {
			Predicate predicate1 = builder.like(builder.lower(root.get("title")),
					"%" + searchPostDTO.getKeyword().toLowerCase() + "%");

			predicates.add(predicate1);
		}

		if (searchPostDTO.getCategoryId() != null) {
			Join<Post, Category> category = root.join("category");

			Predicate predicate = builder.equal(category.get("id"), searchPostDTO.getCategoryId());
			predicates.add(predicate);
		}

		if (searchPostDTO.getCreatedById() != null) {
			Join<Post, User> user = root.join("createdBy");
			Predicate predicate = builder.equal(user.get("id"), searchPostDTO.getCreatedById());
			predicates.add(predicate);
		}

		query.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public long countTotal(SearchPostDTO searchPostDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Post> root = query.from(Post.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}
	 

}
