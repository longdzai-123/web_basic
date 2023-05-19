package HoangLong.web_basic.dao;

import java.util.List;

import HoangLong.web_basic.dto.SearchUserDTO;
import HoangLong.web_basic.entity.User;

public interface UserDao {
	void add(User user);
	
	void delete(User user);
	
	void update(User user);
	
	User getById(Long id);
	
	User getByPhone(String phone);
	
	List<User> find(SearchUserDTO searchUserDTO);
	
	long count(SearchUserDTO searchUserDTO);
	
	long countTotal(SearchUserDTO searchUserDTO);
	

}
