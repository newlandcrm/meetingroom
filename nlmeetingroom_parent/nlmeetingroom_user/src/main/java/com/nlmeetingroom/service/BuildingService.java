package com.nlmeetingroom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.nlmeetingroom.pojo.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.nlmeetingroom.dao.BuildingDao;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class BuildingService {

	@Autowired
	private BuildingDao buildingDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Building> findAll() {
		return buildingDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Building> findSearch(Map whereMap, int page, int size) {
		Specification<Building> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return buildingDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Building> findSearch(Map whereMap) {
		Specification<Building> specification = createSpecification(whereMap);
		return buildingDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Building findById(String id) {
		return buildingDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param building
	 */
	public void add(Building building) {
		building.setId( idWorker.nextId()+"" );
		buildingDao.save(building);
	}

	/**
	 * 修改
	 * @param building
	 */
	public void update(Building building) {
		buildingDao.save(building);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		buildingDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Building> createSpecification(Map searchMap) {

		return new Specification<Building>() {

			@Override
			public Predicate toPredicate(Root<Building> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 楼名
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 权重
                if (searchMap.get("sort")!=null && !"".equals(searchMap.get("sort"))) {
                	predicateList.add(cb.like(root.get("sort").as(String.class), "%"+(String)searchMap.get("sort")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
