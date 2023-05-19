package HoangLong.web_basic.api_controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import HoangLong.web_basic.dto.TokenDTO;
import HoangLong.web_basic.dto.UserDTO;
import HoangLong.web_basic.exception.JwtCustomException;
import HoangLong.web_basic.security.JwtTokenProvider;
import HoangLong.web_basic.service.UserService;
import HoangLong.web_basic.utils.RoleEnum;


@RestController
@RequestMapping("/api")
public class UserAPIController {
	
	@Autowired 
	AuthenticationManager  authenticationManager;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	UserService userService;
	
	// login me
    @PostMapping("/login")
    public TokenDTO login(@RequestParam(name = "phone", required = true) String phone,
    		@RequestParam(name = "password", required = true) String password,
    		@RequestParam(name = "deviceToken", required = false) String token) {
	   try { 	
	    	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
	    	if(token != null) {
	    		UserDTO userDTO = new UserDTO();
	    		userDTO.setPhone(phone);
	    		userDTO.setDeviceId(token);
	    		userService.updateToken(userDTO);
	    	}
	    	
	    	return jwtTokenProvider.createToken(phone);
	   }catch(AuthenticationException e) {
		   throw new JwtCustomException("Invalid username/password supplied", HttpStatus.UNAUTHORIZED);
	   }	
    	
    }
    
    // register 
    @PostMapping("/user/register")
    public UserDTO register(@RequestBody UserDTO userDTO) {
    	userDTO.setEnabled(true);
    	userDTO.setRoles(Arrays.asList("ROLE_"+ RoleEnum.MEMBER.getRoleName()));
    	userService.addUser(userDTO);
    	return userDTO;
    }
}
