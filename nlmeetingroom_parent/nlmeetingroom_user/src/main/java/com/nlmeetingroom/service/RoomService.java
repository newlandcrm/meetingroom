package com.nlmeetingroom.service;

import java.util.ArrayList;
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
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.nlmeetingroom.dao.RoomDao;
import com.nlmeetingroom.pojo.Room;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class RoomService {

	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Room> findAll() {
		return roomDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Room> findSearch(Map whereMap, int page, int size) {
		Specification<Room> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return roomDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Room> findSearch(Map whereMap) {
		Specification<Room> specification = createSpecification(whereMap);
		return roomDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Room findById(String id) {
		return roomDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param room
	 */
	public void add(Room room) {
		room.setId( idWorker.nextId()+"" );
		roomDao.save(room);
	}

	/**
	 * 修改
	 * @param room
	 */
	public void update(Room room) {
		roomDao.save(room);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		roomDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Room> createSpecification(Map searchMap) {

		return new Specification<Room>() {

			@Override
			public Predicate toPredicate(Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 会议室名字
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 所属楼层
                if (searchMap.get("floorid")!=null && !"".equals(searchMap.get("floorid"))) {
                	predicateList.add(cb.like(root.get("floorid").as(String.class), "%"+(String)searchMap.get("floorid")+"%"));
                }
                // 会议室地址
                if (searchMap.get("addr")!=null && !"".equals(searchMap.get("addr"))) {
                	predicateList.add(cb.like(root.get("addr").as(String.class), "%"+(String)searchMap.get("addr")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
