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

import HoangLong.web_basic.dao.NotificationDao;
import HoangLong.web_basic.dto.SearchDTO;
import HoangLong.web_basic.entity.Notification;


@Repository
@Transactional
public class NotificationDaoImpl extends JPARepository<Notification> implements NotificationDao {
	public Notification getNotificationId(Long id) {
		return entityManager.find(Notification.class, id);
	}

	@Override
	public List<Notification> find(SearchDTO searchDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Notification> criteriaQuery = criteriaBuilder.createQuery(Notification.class);
		Root<Notification> root = criteriaQuery.from(Notification.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchDTO.getKeyword())) {
			Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.lower(root.get("content")),
					"%" + searchDTO.getKeyword().toLowerCase() + "%");

			predicates.add(predicate1);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		// order
		if (StringUtils.equals(searchDTO.getSortBy().getData(), "id")) {
			if (searchDTO.getSortBy().isAsc()) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
			}
		}

		TypedQuery<Notification> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
		if (searchDTO.getStart() != null) {
			typedQuery.setFirstResult((searchDTO.getStart()));
			typedQuery.setMaxResults(searchDTO.getLength());
		}
		return typedQuery.getResultList();
	}

	@Override
	public Long count(SearchDTO searchDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Notification> root = criteriaQuery.from(Notification.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.isNotBlank(searchDTO.getKeyword())) {
			Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.lower(root.get("content")),
					"%" + searchDTO.getKeyword().toLowerCase() + "%");

			predicates.add(predicate1);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long countTotal(SearchDTO searchDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Notification> root = criteriaQuery.from(Notification.class);

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}
}
