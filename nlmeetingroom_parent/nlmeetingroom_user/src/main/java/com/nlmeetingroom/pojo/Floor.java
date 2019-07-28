package com.nlmeetingroom.pojo;

import javax.persistence.*;
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
	@ManyToOne(targetEntity = Building.class)
	@JoinColumn(name = "buildingid",referencedColumnName = "id")
	private Building building;
	
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

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}
}
