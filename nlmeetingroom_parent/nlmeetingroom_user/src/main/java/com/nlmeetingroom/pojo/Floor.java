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
	@Column(insertable = false, updatable = false)
	private String buildingid;
	private String describes;//描述
	private Integer floors;//楼层
	@ManyToOne(targetEntity = Building.class)
	@JoinColumn(name = "buildingid",referencedColumnName = "id")
	private Building building;

	public String getBuildingid() {
		return buildingid;
	}

	public void setBuildingid(String buildingid) {
		this.buildingid = buildingid;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public Integer getFloors() {
		return floors;
	}
	public void setFloors(Integer floors) {
		this.floors = floors;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	@Override
	public String toString() {
		return "Floor{" +
				"id='" + id + '\'' +
				", buildingid='" + buildingid + '\'' +
				", describe='" + describes + '\'' +
				", floor=" + floors +
				", building=" + building.toString() +
				'}';
	}
}
