package HoangLong.web_basic.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import HoangLong.web_basic.dao.NotificationDao;
import HoangLong.web_basic.dto.NotificationDTO;
import HoangLong.web_basic.dto.SearchDTO;
import HoangLong.web_basic.entity.Notification;
import HoangLong.web_basic.service.NotificationService;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	NotificationDao notificationDao;

	@Override
	public void add(NotificationDTO notificationDTO) {
		Notification notification = new ModelMapper().map(notificationDTO, Notification.class);
		notificationDao.add(notification);
		notificationDTO.setId(notification.getId());
	}

	@Override
	public void delete(Long id) {
		Notification notification = notificationDao.getNotificationId(id);
		notificationDao.delete(notification);
		
	}

	@Override
	public List<NotificationDTO> find(SearchDTO searchDTO) {
		List<Notification> notifications = notificationDao.find(searchDTO);
		List<NotificationDTO> notificationDTOs = new ArrayList<NotificationDTO>();
		for (Notification notification : notifications) {
			NotificationDTO notificationDTO = new ModelMapper().map(notification, NotificationDTO.class);
			notificationDTOs.add(notificationDTO);
		}
		return notificationDTOs;
	}

	@Override
	public Long count(SearchDTO searchDTO) {
		
		return notificationDao.count(searchDTO);
	}

	@Override
	public Long countTotal(SearchDTO searchDTO) {
		return notificationDao.countTotal(searchDTO);
	}

}
