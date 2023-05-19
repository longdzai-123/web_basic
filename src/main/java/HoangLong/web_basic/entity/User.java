package HoangLong.web_basic.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends CreateAuditable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "phone", unique = true)
	private String phone;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "device_id", columnDefinition = "TEXT")
	private String deviceId;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_role" , joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	private List<String> roles;
	
	private int point;
	
	

}
