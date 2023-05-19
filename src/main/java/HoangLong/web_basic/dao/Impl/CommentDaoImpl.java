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

import HoangLong.web_basic.dao.CommentDao;
import HoangLong.web_basic.dto.SearchCommentDTO;
import HoangLong.web_basic.entity.Comment;
import HoangLong.web_basic.entity.Post;
import HoangLong.web_basic.entity.User;

@Repository
@Transactional
public class CommentDaoImpl extends JPARepository<Comment> implements CommentDao{

	@Override
	public Comment get(Long id) {
		
		return entityManager.find(Comment.class, id);
	}

	@Override
	public List<Comment> find(SearchCommentDTO searchCommentDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Comment> query = builder.createQuery(Comment.class);
		Root<Comment> root = query.from(Comment.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(StringUtils.isNotBlank(searchCommentDTO.getKeyword())) {
			Predicate predicate = builder.like(builder.lower(root.get("content")), "%"+searchCommentDTO.getKeyword().toLowerCase()+"%");
			predicates.add(predicate);
		}
		if(searchCommentDTO.getUserId() != null) {
			Join<Comment, User> comment = root.join("user");
			Predicate predicate = builder.equal(comment.get("id"), searchCommentDTO.getUserId());
			predicates.add(predicate);
		}
		if (searchCommentDTO.getPostId() != null) {
			Join<Comment, Post> comment = root.join("post");

			Predicate predicate = builder.equal(comment.get("id"), searchCommentDTO.getPostId());
			predicates.add(predicate);
		}
		query.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchCommentDTO.getSortBy().getData(), "id")) {
			if (searchCommentDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("id")));
			} else {
				query.orderBy(builder.desc(root.get("id")));
			}
		} else if (StringUtils.equals(searchCommentDTO.getSortBy().getData(), "content")) {
			if (searchCommentDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("content")));
			} else {
				query.orderBy(builder.desc(root.get("content")));
			}
		}

		TypedQuery<Comment> typedQuery = entityManager.createQuery(query.select(root));
		if (searchCommentDTO.getStart() != null) {
			typedQuery.setFirstResult((searchCommentDTO.getStart()));
			typedQuery.setMaxResults(searchCommentDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long count(SearchCommentDTO searchCommentDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Comment> root = query.from(Comment.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchCommentDTO.getKeyword())) {
			Predicate predicate1 = builder.like(builder.lower(root.get("content")),
					"%" + searchCommentDTO.getKeyword().toLowerCase() + "%");
			predicates.add(predicate1);
		}

		if (searchCommentDTO.getUserId() != null) {
			Join<Comment, User> comment = root.join("user");

			Predicate predicate = builder.equal(comment.get("id"), searchCommentDTO.getUserId());
			predicates.add(predicate);
		}

		if (searchCommentDTO.getPostId() != null) {
			Join<Comment, Post> comment = root.join("post");

			Predicate predicate = builder.equal(comment.get("id"), searchCommentDTO.getPostId());
			predicates.add(predicate);
		}

		query.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotal(SearchCommentDTO searchCommentDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Comment> root = query.from(Comment.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchCommentDTO.getPostId() != null) {
			Join<Comment, Post> comment = root.join("post");

			Predicate predicate = builder.equal(comment.get("id"), searchCommentDTO.getPostId());
			predicates.add(predicate);
		}
		query.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}

    
}
