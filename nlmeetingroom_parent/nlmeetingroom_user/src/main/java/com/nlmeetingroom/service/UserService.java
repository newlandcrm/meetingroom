package com.nlmeetingroom.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.nlmeetingroom.dao.UserDao;
import com.nlmeetingroom.pojo.User;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;

    @Autowired
    BCryptPasswordEncoder encoder;

	//修改密码
	public void updatePassword(Map searchMap) {
		String userid= (String) searchMap.get("userid");
		String oldpassword= (String) searchMap.get("oldpassword");
		String newpassword= (String) searchMap.get("newpassword");
		newpassword = encoder.encode(newpassword);//加密后的密码

		System.out.println("旧密码"+oldpassword);
		User user = userDao.findById(userid).get();

		if(user!=null && encoder.matches(oldpassword,user.getPassword())){
			user.setPassword(newpassword);
			userDao.save(user);
		}else{
			throw new RuntimeException("密码错误");
		}



	}
	/**
	 * 根据账号和密码查询管理员
	 * @param userName
	 * @param password
	 * @return
	 */
	public User loginByUsernameAndPassword(String userName,String password,String flag){
		User user = userDao.findByUsernameAndRoleid(userName,flag);
		if(user!=null && encoder.matches(password,user.getPassword())){
			user.setLastdate(new Date());//最后登陆日期
			userDao.save(user);
			return user;
		}else{
			return null;
		}
	}



	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user) throws Exception {
		User user1 = userDao.findByUsernameAndRoleid(user.getUsername(), "0");
		if (user1!=null){
			throw new Exception("该用户已存在");
		}
		user.setId( idWorker.nextId()+"" );
        user.setRegdate(new Date());//注册日期
        user.setLastdate(new Date());//最后登陆日期
		user.setRoleid("0");
        //密码加密
        String newpassword = encoder.encode(user.getPassword());//加密后的密码
        user.setPassword(newpassword);
        userDao.save(user);
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 账号
                if (searchMap.get("username")!=null && !"".equals(searchMap.get("username"))) {
                	predicateList.add(cb.like(root.get("username").as(String.class), "%"+(String)searchMap.get("username")+"%"));
                }
                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                	predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 角色：初期暂定0 普通1 管理员 后期再加哥角色表
                if (searchMap.get("roleid")!=null && !"".equals(searchMap.get("roleid"))) {
                	predicateList.add(cb.like(root.get("roleid").as(String.class), "%"+(String)searchMap.get("roleid")+"%"));
                }
                // 所属部门id
                if (searchMap.get("departmentid")!=null && !"".equals(searchMap.get("departmentid"))) {
                	predicateList.add(cb.like(root.get("departmentid").as(String.class), "%"+(String)searchMap.get("departmentid")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
