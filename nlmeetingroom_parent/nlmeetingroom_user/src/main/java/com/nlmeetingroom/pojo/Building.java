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
@Table(name="building")
public class Building implements Serializable{

	@Id
	private String id;//


	
	private String name;//楼名
	private String sort;//权重

	
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


	
}
