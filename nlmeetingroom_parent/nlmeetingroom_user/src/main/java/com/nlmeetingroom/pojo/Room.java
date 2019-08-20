package com.nlmeetingroom.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
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
	private Integer capacity;//会议室容量
	private String addr;//会议室地址
	private Integer openstate;//开放状态1:开放 0：不开放
	@Column(insertable = false, updatable = false)
	private String floorid;
	@ManyToOne(targetEntity = Floor.class)
	@JoinColumn(name = "floorid",referencedColumnName = "id")
	private Floor floor;

	public String getFloorid() {
		return floorid;
	}

	public void setFloorid(String floorid) {
		this.floorid = floorid;
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

	public Floor getFloor() {
		return floor;
	}
	public void setFloor(Floor floor) {
		this.floor = floor;
	}

	@Override
	public String toString() {
		return "Room{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", capacity=" + capacity +
				", addr='" + addr + '\'' +
				", openstate=" + openstate +
				", floorid='" + floorid + '\'' +
				", floor=" + floor +
				'}';
	}
}
