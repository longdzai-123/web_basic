package HoangLong.web_basic.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
	
	private Long id;
	
	private String name;

	private String password;
	
	private String phone;
	
	private String newPassword;
	
	private String address;
	
	private String deviceId;
	
	private Boolean enabled;
	
	private String createdDate;
	
	private String createdBy;

	private Long createdById;
	
	private List<String> roles;
	
	private int point;
	
}
