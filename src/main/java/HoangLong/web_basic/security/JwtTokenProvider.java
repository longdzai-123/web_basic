package HoangLong.web_basic.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import HoangLong.web_basic.dto.TokenDTO;
import HoangLong.web_basic.exception.JwtCustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
   @Value("${jwt.secret:123456}")
   private String secretKey;
   
   private long validityInMilliseconds = 3600000; //1h
   
   @Autowired 
   UserDetailsService userDetailsService;
   
   public TokenDTO createToken(String username) {
	   Claims claims = Jwts.claims().setSubject(username);
	   Date now = new Date();
	   
	   Date validity = new Date(now.getTime() + validityInMilliseconds);
	   
	   String accessToken = Jwts.builder()
			   .setClaims(claims)
			   .setIssuedAt(now)
			   .setExpiration(validity)
			   .signWith(SignatureAlgorithm.HS256, secretKey)
			   .compact();
	   TokenDTO tokenDTO = new TokenDTO();
	   tokenDTO.setAccessToken(accessToken);
	   tokenDTO.setExpirationTime(validityInMilliseconds);
	   return tokenDTO;
	   
   }
   public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
   
   public String resolveToken(HttpServletRequest request) {
	   String bearerToken = request.getHeader("Authorization");
	   if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
   }
   
   
   public boolean validateToken(String token) {
	   try {
		   Jwts.parser().setSigningKey(secretKey).parsePlaintextJws(token);
		   return true;
	   }catch (JwtException | IllegalArgumentException e) {
		   throw new JwtCustomException("Expired or invalid JWT token", HttpStatus.UNAUTHORIZED);
	}
   }
}
