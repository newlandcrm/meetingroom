package com.nlmeetingroom.dao;

import com.nlmeetingroom.pojo.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface BuildingDao extends JpaRepository<Building,String>,JpaSpecificationExecutor<Building>{
	
}
