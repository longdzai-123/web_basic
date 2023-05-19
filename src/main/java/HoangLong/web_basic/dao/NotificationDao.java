package HoangLong.web_basic.dao;

import java.util.List;

import HoangLong.web_basic.dto.SearchDTO;
import HoangLong.web_basic.entity.Notification;



public interface NotificationDao {
	void add(Notification notification);

	void delete(Notification notification);

	void update(Notification notification);

	Notification getNotificationId(Long id);

	List<Notification> find(SearchDTO searchDTO);

	Long count(SearchDTO searchDTO);

	Long countTotal(SearchDTO searchDTO);
}
