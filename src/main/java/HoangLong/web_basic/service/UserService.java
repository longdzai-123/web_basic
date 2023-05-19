package HoangLong.web_basic.service;

import java.util.List;

import HoangLong.web_basic.dto.SearchUserDTO;
import HoangLong.web_basic.dto.UserDTO;

public interface UserService {
	void addUser(UserDTO userDTO);
	
	void deleteUser(Long id);
	
	void updateUser(UserDTO userDTO);
	
	List<UserDTO> find(SearchUserDTO searchUserDTO);
	
	UserDTO getUserById(Long id);
	
	long count(SearchUserDTO searchUserDTO);
	
	long countTotal(SearchUserDTO searchUserDTO);
	
	void updateToken(UserDTO userDTO);
	
	void changePassword(UserDTO userDTO);
	
	void setupUserPassword(UserDTO userDTO);
	
	
	
	

}
