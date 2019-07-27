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
@Table(name="floor")
public class Floor implements Serializable{

	@Id
	private String id;//


	
	private String describe;//描述
	private Integer floor;//楼层
	private String buildingid;//所属楼

	
	public String getId() {		
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getDescribe() {		
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Integer getFloor() {		
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public String getBuildingid() {		
		return buildingid;
	}
	public void setBuildingid(String buildingid) {
		this.buildingid = buildingid;
	}


	
}
