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

import com.nlmeetingroom.dao.RelatePersonDao;
import com.nlmeetingroom.pojo.RelatePerson;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class RelatePersonService {

	@Autowired
	private RelatePersonDao relatePersonDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<RelatePerson> findAll() {
		return relatePersonDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<RelatePerson> findSearch(Map whereMap, int page, int size) {
		Specification<RelatePerson> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return relatePersonDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<RelatePerson> findSearch(Map whereMap) {
		Specification<RelatePerson> specification = createSpecification(whereMap);
		return relatePersonDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public RelatePerson findById(String id) {
		return relatePersonDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param relatePerson
	 */
	public void add(RelatePerson relatePerson) {
		relatePerson.setId( idWorker.nextId()+"" );
		relatePersonDao.save(relatePerson);
	}

	/**
	 * 修改
	 * @param relatePerson
	 */
	public void update(RelatePerson relatePerson) {
		relatePersonDao.save(relatePerson);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		relatePersonDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<RelatePerson> createSpecification(Map searchMap) {

		return new Specification<RelatePerson>() {

			@Override
			public Predicate toPredicate(Root<RelatePerson> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 
                if (searchMap.get("reserveid")!=null && !"".equals(searchMap.get("reserveid"))) {
                	predicateList.add(cb.like(root.get("reserveid").as(String.class), "%"+(String)searchMap.get("reserveid")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

    public void addList(String reserveid, ArrayList<String> arrayList) {
		relatePersonDao.deleteByReserveid(reserveid);
		for (String name:arrayList) {
			RelatePerson relatePerson = new RelatePerson();
			relatePerson.setName(name);
			relatePerson.setReserveid(reserveid);
			add(relatePerson);
		}
    }
}
