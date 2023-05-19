package HoangLong.web_basic.api_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import HoangLong.web_basic.dto.NotificationDTO;
import HoangLong.web_basic.dto.ResponseDTO;
import HoangLong.web_basic.dto.SearchDTO;
import HoangLong.web_basic.service.NotificationService;


@RestController
@RequestMapping("/api")
public class NotificationAPIController {
	 @Autowired
	 NotificationService notificationService;

     @PostMapping("/notification/search")
     public ResponseDTO<NotificationDTO> find(@RequestBody SearchDTO searchDTO) {
 		ResponseDTO<NotificationDTO> responseDTO = new ResponseDTO<NotificationDTO>();
 		responseDTO.setData(notificationService.find(searchDTO));
 		responseDTO.setRecordsFiltered(notificationService.count(searchDTO));
 		responseDTO.setRecordsTotal(notificationService.countTotal(searchDTO));
 		return responseDTO;
     }
}