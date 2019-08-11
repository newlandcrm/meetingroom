package com.nlmeetingroom.pojo;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="user_room_reserve")
public class RoomReserve implements Serializable{
	public static final Integer EXAMINE_STATE_NOT_EXAMINE=0;//未审核
	public static final Integer EXAMINE_STATE_SUCCESS=1;//审核 成功
	public static final Integer EXAMINE_STATE_FAIL=2;//审核 未通过
	@Id
	private String id;//


	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "userid",referencedColumnName = "id")
	private User user;//申请人
	@ManyToOne(targetEntity = Room.class)
	@JoinColumn(name = "roomid",referencedColumnName = "id")
	private Room room;//会议室
	@Column(insertable = false, updatable = false)
	private String roomid;
	@Column(insertable = false, updatable = false)
	private String userid;

	private java.util.Date startdate;//开始时间
	private java.util.Date enddate;//结束时间
	private String content;//内容
	private Integer state;//审核状态

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
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

	@Override
	public String toString() {
		return "RoomReserve{" +
				"id='" + id + '\'' +
				", user=" + user +
				", room=" + room +
				", roomid='" + roomid + '\'' +
				", userid='" + userid + '\'' +
				", startdate=" + startdate +
				", enddate=" + enddate +
				", content='" + content + '\'' +
				", state=" + state +
				'}';
	}
}
