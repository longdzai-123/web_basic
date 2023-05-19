package HoangLong.web_basic.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import HoangLong.web_basic.dao.UserDao;
import HoangLong.web_basic.dto.SearchUserDTO;
import HoangLong.web_basic.dto.UserDTO;
import HoangLong.web_basic.dto.UserPrincipal;
import HoangLong.web_basic.entity.User;
import HoangLong.web_basic.service.UserService;
import HoangLong.web_basic.utils.PasswordGenerator;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService{
	@Autowired
	UserDao userDao;
	
	@Override
	public void addUser(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setPhone(userDTO.getPhone());
		user.setPassword(PasswordGenerator.getHashString(userDTO.getPassword()));
		user.setEnabled(userDTO.getEnabled());
		if(userDTO.getRoles() != null) {
			user.setRoles(userDTO.getRoles());
		}
		user.setAddress(userDTO.getAddress());
		user.setPoint(2000000);
		userDao.add(user);
		userDTO.setId(user.getId());
	}
	
	@Override
	public void updateUser(UserDTO userDTO) {
		User user = userDao.getById(userDTO.getId());
		if(user != null) {
			user.setName(userDTO.getName());
			user.setAddress(userDTO.getAddress());
			user.setPhone(userDTO.getPhone());
			userDao.update(user);
		}
	}
	
	@Override
	public void deleteUser(Long id) {
		User user = userDao.getById(id);
		if (user != null) {
			userDao.delete(user);
		}
	}

	@Override
	public UserDTO getUserById(Long id) {
		User user = userDao.getById(id);
		if(user != null) {
			UserDTO userDTO = new ModelMapper().map(user, UserDTO.class);
			return userDTO;
		}
		return null;	
	}
	@Override
	public List<UserDTO> find(SearchUserDTO searchUserDTO){
		List<User> users = userDao.find(searchUserDTO);
		List<UserDTO> userDTOs = new ArrayList<>();
		for(User user : users) {
			UserDTO userDTO = new ModelMapper().map(user, UserDTO.class);
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}
	
	@Override
	public long count(SearchUserDTO searchUserDTO) {
	    return userDao.count(searchUserDTO);
	}
	@Override
	public long countTotal(SearchUserDTO searchUserDTO) {
		return userDao.countTotal(searchUserDTO);
	}
	
	@Override
	public void updateToken(UserDTO userDTO) {
		User user = userDao.getByPhone(userDTO.getPhone());
		if(user != null) {
			String token = user.getDeviceId();
			if(token == null) {
				token = token + userDTO.getDeviceId();
			}else if (!token.contains(userDTO.getDeviceId())) {
				token = token + "," + userDTO.getDeviceId();
			}
            user.setDeviceId(token);
            
            userDao.update(user);
		}		
	}
	

	@Override
	public void changePassword(UserDTO userDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setupUserPassword(UserDTO userDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getByPhone(username.trim());
		if(user == null) {
			throw new UsernameNotFoundException("not found");
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for (String role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		
//		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(), true, true, true, true, authorities);
//		return userDetails;
		UserPrincipal userPrincipal = new UserPrincipal(user.getPhone(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
		userPrincipal.setId(user.getId());
		userPrincipal.setName(user.getName());
		userPrincipal.setRoles(user.getRoles());
		
		return userPrincipal;
	}
	
}
