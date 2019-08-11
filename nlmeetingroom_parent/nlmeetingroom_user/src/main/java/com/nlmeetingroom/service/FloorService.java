package com.nlmeetingroom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.nlmeetingroom.dao.BuildingDao;
import com.nlmeetingroom.pojo.Building;
import com.nlmeetingroom.pojo.Floor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.nlmeetingroom.dao.FloorDao;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class FloorService {

	@Autowired
	private FloorDao floorDao;
	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Floor> findAll() {
		return floorDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Floor> findSearch(Map whereMap, int page, int size) {
		Specification<Floor> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return floorDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Floor> findSearch(Map whereMap) {
		Specification<Floor> specification = createSpecification(whereMap);
		return floorDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Floor findById(String id) {
		return floorDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param floor
	 */
	public void add(Floor floor) {
		floor.setId( idWorker.nextId()+"" );
		floor.setBuildingid(floor.getBuilding().getId());
		Building building=buildingDao.getOne(floor.getBuilding().getId());
		floor.setBuilding(building);
		floorDao.save(floor);
	}

	/**
	 * 修改
	 * @param floor
	 */
	public void update(Floor floor) {
		floorDao.save(floor);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		floorDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Floor> createSpecification(Map searchMap) {

		return new Specification<Floor>() {

			@Override
			public Predicate toPredicate(Root<Floor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 描述
                if (searchMap.get("describes")!=null && !"".equals(searchMap.get("describes"))) {
                	predicateList.add(cb.like(root.get("describes").as(String.class), "%"+(String)searchMap.get("describes")+"%"));
                }
                // 所属楼
                if (searchMap.get("buildingid")!=null && !"".equals(searchMap.get("buildingid"))) {
                	predicateList.add(cb.like(root.get("buildingid").as(String.class), "%"+(String)searchMap.get("buildingid")+"%"));
                }

				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
