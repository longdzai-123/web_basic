package HoangLong.web_basic.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseDTO<T> {
	private long recordsTotal;
	private long recordsFiltered;
	private List<T> data;

}
