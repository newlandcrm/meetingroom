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
@Table(name="user_room_reserve")
public class RoomReserve implements Serializable{

	@Id
	private String id;//


	
	private String userid;//申请人id
	private String roomid;//会议室id
	private java.util.Date startdate;//开始时间
	private java.util.Date enddate;//结束时间
	private String content;//内容
	private Integer state;//审核状态

	
	public String getId() {		
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {		
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRoomid() {		
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public java.util.Date getStartdate() {		
		return startdate;
	}
	public void setStartdate(java.util.Date startdate) {
		this.startdate = startdate;
	}

	public java.util.Date getEnddate() {		
		return enddate;
	}
	public void setEnddate(java.util.Date enddate) {
		this.enddate = enddate;
	}

	public String getContent() {		
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
