package HoangLong.web_basic.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchUserDTO extends SearchDTO {
	private Boolean enabled;
	private List<String> roleList;
}
