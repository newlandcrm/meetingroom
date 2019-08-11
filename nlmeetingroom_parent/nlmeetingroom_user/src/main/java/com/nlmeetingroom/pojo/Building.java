package com.nlmeetingroom.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="building")
public class Building implements Serializable{

	@Id
	private String id;//


	
	private String name;//楼名
	private String sort;//权重
	@Transient
	private List<Floor> floors;

	public List<Floor> getFloors() {
		return floors;
	}

	public void setFloors(List<Floor> floors) {
		this.floors = floors;
	}

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

	public String getSort() {		
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "Building{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", sort='" + sort + '\'' +
				'}';
	}
}
