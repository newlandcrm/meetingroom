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

import com.nlmeetingroom.dao.DepartmentDao;
import com.nlmeetingroom.pojo.Department;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class DepartmentService {

	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Department> findAll() {
		return departmentDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Department> findSearch(Map whereMap, int page, int size) {
		Specification<Department> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return departmentDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Department> findSearch(Map whereMap) {
		Specification<Department> specification = createSpecification(whereMap);
		return departmentDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Department findById(String id) {
		return departmentDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param department
	 */
	public void add(Department department) {
		department.setId( idWorker.nextId()+"" );
		departmentDao.save(department);
	}

	/**
	 * 修改
	 * @param department
	 */
	public void update(Department department) {
		departmentDao.save(department);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		departmentDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Department> createSpecification(Map searchMap) {

		return new Specification<Department>() {

			@Override
			public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 部门名字
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
