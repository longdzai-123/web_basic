package HoangLong.web_basic.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import HoangLong.web_basic.dao.UserDao;
import HoangLong.web_basic.dto.SearchUserDTO;
import HoangLong.web_basic.entity.User;

@Repository
@Transactional // neu co mot loi xay ra trong transactional thi tat ca cac tac vu se that bai
public class UserDaoImpl extends JPARepository<User> implements UserDao{
    
	@Override
	public User getById(Long id) {
		return entityManager.find(User.class, id);
	}
	@Override
	public User getByPhone(String phone) {
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			
			CriteriaQuery<User> query = builder.createQuery(User.class);
			
			Root<User> root = query.from(User.class);
			
			query.where(builder.equal(builder.lower(root.get("phone")),phone));
			
			TypedQuery<User> typedQuery = entityManager.createQuery(query.select(root).distinct(true));
			
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	@Override
	public List<User> find(SearchUserDTO searchUserDTO) {
	    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<User> query = builder.createQuery(User.class);
	    Root<User> root = query.from(User.class);
	    
	    // Constructing list of parameters
	    List<Predicate> predicates = new ArrayList<Predicate>();
	    if(StringUtils.isNotBlank(searchUserDTO.getKeyword())) {
	    	Predicate predicate1 = builder.like(builder.lower(root.get("phone")), "%" + searchUserDTO.getKeyword().toLowerCase()+"%");
	    	Predicate predicate2 = builder.like(builder.lower(root.get("name")), "%" + searchUserDTO.getKeyword().toLowerCase()+"%");
	    	predicates.add(builder.or(predicate2, predicate1));
	    	
	    }
	    
	    if(searchUserDTO.getRoleList() != null) {
	    	predicates.add(root.join("roles").in(searchUserDTO.getRoleList()));
	    }
	    if(searchUserDTO.getEnabled() != null) {
	    	predicates.add(builder.equal(root.get("enabled"),searchUserDTO.getEnabled()));
	    }
	    
	    query.where(predicates.toArray(new Predicate[] {}));
	    
	    //order
	    if(StringUtils.equals(searchUserDTO.getSortBy().getData(), "id")) {
	    	if(searchUserDTO.getSortBy().isAsc()) {
	    		query.orderBy(builder.asc(root.get("id")));
	    	}else {
	    		query.orderBy(builder.desc(root.get("id")));
	    	}
	    }else if (StringUtils.equals(searchUserDTO.getSortBy().getData(), "name")) {
			if (searchUserDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("name")));
			} else {
				query.orderBy(builder.desc(root.get("name")));
			}
		} else if (StringUtils.equals(searchUserDTO.getSortBy().getData(), "createdDate")) {
			if (searchUserDTO.getSortBy().isAsc()) {
				query.orderBy(builder.asc(root.get("createdDate")));
			} else {
				query.orderBy(builder.desc(root.get("createdDate")));
			}
		}   
	    TypedQuery<User> typedQuery = entityManager.createQuery(query.select(root).distinct(true));
		if (searchUserDTO.getStart() != null) {
			typedQuery.setFirstResult(searchUserDTO.getStart());
			typedQuery.setMaxResults(searchUserDTO.getLength());
		}
		return typedQuery.getResultList();
	}
	@Override
	public long count(SearchUserDTO searchUserDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Long> query = builder.createQuery(Long.class);
	    Root<User> root = query.from(User.class);
	    
	    // Constructing list of parameters
	    List<Predicate> predicates = new ArrayList<Predicate>();
	    if(StringUtils.isNotBlank(searchUserDTO.getKeyword())) {
	    	Predicate predicate1 = builder.like(builder.lower(root.get("phone")), "%" + searchUserDTO.getKeyword().toLowerCase()+"%");
	    	Predicate predicate2 = builder.like(builder.lower(root.get("name")), "%" + searchUserDTO.getKeyword().toLowerCase()+"%");
	    	predicates.add(builder.or(predicate2, predicate1));
	    	
	    }
	    
	    if(searchUserDTO.getRoleList() != null) {
	    	predicates.add(root.join("roles").in(searchUserDTO.getRoleList()));
	    }
	    if(searchUserDTO.getEnabled() != null) {
	    	predicates.add(builder.equal(root.get("enabled"),searchUserDTO.getEnabled()));
	    }
	    
	    query.where(predicates.toArray(new Predicate[] {}));
	    TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}
	@Override
	public long countTotal(SearchUserDTO searchUserDTO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Long> query = builder.createQuery(Long.class);
	    Root<User> root = query.from(User.class);
	    
	    TypedQuery<Long> typedQuery = entityManager.createQuery(query.select(builder.count(root)));
		return typedQuery.getSingleResult();
	}
	
	
	
}
