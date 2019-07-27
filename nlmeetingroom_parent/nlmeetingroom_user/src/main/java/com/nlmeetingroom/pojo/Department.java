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
@Table(name="department")
public class Department implements Serializable{

	@Id
	private String id;//


	
	private String name;//部门名字
	private String another;//预留字段

	
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

	public String getAnother() {		
		return another;
	}
	public void setAnother(String another) {
		this.another = another;
	}


	
}
