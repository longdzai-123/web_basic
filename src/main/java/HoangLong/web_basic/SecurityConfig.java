package HoangLong.web_basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import HoangLong.web_basic.security.JwtTokenFilter;
import HoangLong.web_basic.security.JwtTokenProvider;
import HoangLong.web_basic.utils.RoleEnum;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	//xac thuc
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder(12));
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests()
		    .antMatchers("/api/adimin/**").hasAnyRole(RoleEnum.ADMIN.getRoleName())
		    .antMatchers("/api/member/**").authenticated()
		    .anyRequest().permitAll()
		    .and().httpBasic();
		// Apply JWT
		http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
	}
	
}
