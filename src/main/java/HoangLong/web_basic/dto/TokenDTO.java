package HoangLong.web_basic.dto;

import groovy.transform.builder.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {
     private String accessToken;
     private Long expirationTime;
}
