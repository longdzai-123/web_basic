package HoangLong.web_basic.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import HoangLong.web_basic.exception.JwtCustomException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
     private JwtTokenProvider jwtTokenProvider;
     
     public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    	 this.jwtTokenProvider = jwtTokenProvider;
     }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = jwtTokenProvider.resolveToken(request);
		try {
			if(token != null && jwtTokenProvider.validateToken(token)) {
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (JwtCustomException ex) {
			// this is very important, since it guarantees the user is not authenticated at
						// all
			SecurityContextHolder.clearContext();
			response.sendError(ex.getHttpStatus().value(), ex.getMessage());
			return;
		}
		
		filterChain.doFilter(request, response);
	}
     
     
}
