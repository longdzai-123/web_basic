package HoangLong.web_basic.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchPostDTO extends SearchDTO{
      private Long createdById;
      
      private Long categoryId;
      
}
