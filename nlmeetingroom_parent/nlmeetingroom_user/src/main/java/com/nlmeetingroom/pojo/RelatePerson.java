package com.nlmeetingroom.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="reserve_relate_person")
public class RelatePerson implements Serializable{

	@Id
	private String id;//


	
	private String name;//
	private String mobile;//
	private String email;//
	private String reserveid;//

	
	public String getId() {		
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {		
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {		
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getReserveid() {		
		return reserveid;
	}
	public void setReserveid(String reserveid) {
		this.reserveid = reserveid;
	}


	
}
