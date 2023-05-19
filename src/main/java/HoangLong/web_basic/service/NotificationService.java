package HoangLong.web_basic.service;

import java.util.List;

import HoangLong.web_basic.dto.NotificationDTO;
import HoangLong.web_basic.dto.SearchDTO;


public interface NotificationService {
	void add(NotificationDTO notificationDTO);

	void delete(Long id);

	List<NotificationDTO> find(SearchDTO searchDTO);

	Long count(SearchDTO searchDTO);

	Long countTotal(SearchDTO searchDTO);
}
