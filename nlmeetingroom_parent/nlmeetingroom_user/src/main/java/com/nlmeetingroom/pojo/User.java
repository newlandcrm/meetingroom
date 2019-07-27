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
@Table(name="user")
public class User implements Serializable{

	@Id
	private String id;//ID


	
	private String username;//账号
	private String mobile;//手机号码
	private String password;//密码
	private String nickname;//昵称
	private String avatar;//头像
	private String email;//E-Mail
	private java.util.Date regdate;//注册日期
	private java.util.Date lastdate;//最后登陆日期
	private String roleid;//角色：初期暂定0 普通1 管理员 后期再加哥角色表
	private String departmentid;//所属部门id

	
	public String getId() {		
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {		
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {		
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {		
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {		
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {		
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {		
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public java.util.Date getRegdate() {		
		return regdate;
	}
	public void setRegdate(java.util.Date regdate) {
		this.regdate = regdate;
	}

	public java.util.Date getLastdate() {		
		return lastdate;
	}
	public void setLastdate(java.util.Date lastdate) {
		this.lastdate = lastdate;
	}

	public String getRoleid() {		
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getDepartmentid() {		
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}


	
}
