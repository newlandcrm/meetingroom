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
@Table(name="room")
public class Room implements Serializable{

	@Id
	private String id;//


	
	private String name;//会议室名字
	private String floorid;//所属楼层
	private Integer capacity;//会议室容量
	private String addr;//会议室地址
	private Integer openstate;//开放状态0:开放 1：不开放

	
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

	public String getFloorid() {		
		return floorid;
	}
	public void setFloorid(String floorid) {
		this.floorid = floorid;
	}

	public Integer getCapacity() {		
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getAddr() {		
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Integer getOpenstate() {		
		return openstate;
	}
	public void setOpenstate(Integer openstate) {
		this.openstate = openstate;
	}


	
}
