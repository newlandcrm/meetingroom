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

import com.nlmeetingroom.dao.RoomReserveDao;
import com.nlmeetingroom.pojo.RoomReserve;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class RoomReserveService {

	@Autowired
	private RoomReserveDao roomReserveDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<RoomReserve> findAll() {
		return roomReserveDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<RoomReserve> findSearch(Map whereMap, int page, int size) {
		Specification<RoomReserve> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return roomReserveDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<RoomReserve> findSearch(Map whereMap) {
		Specification<RoomReserve> specification = createSpecification(whereMap);
		return roomReserveDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public RoomReserve findById(String id) {
		return roomReserveDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param roomReserve
	 */
	public void add(RoomReserve roomReserve) {
		roomReserve.setId( idWorker.nextId()+"" );
		roomReserveDao.save(roomReserve);
	}

	/**
	 * 修改
	 * @param roomReserve
	 */
	public void update(RoomReserve roomReserve) {
		roomReserveDao.save(roomReserve);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		roomReserveDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<RoomReserve> createSpecification(Map searchMap) {

		return new Specification<RoomReserve>() {

			@Override
			public Predicate toPredicate(Root<RoomReserve> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 申请人id
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 会议室id
                if (searchMap.get("roomid")!=null && !"".equals(searchMap.get("roomid"))) {
                	predicateList.add(cb.like(root.get("roomid").as(String.class), "%"+(String)searchMap.get("roomid")+"%"));
                }
                // 内容
                if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
                	predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
