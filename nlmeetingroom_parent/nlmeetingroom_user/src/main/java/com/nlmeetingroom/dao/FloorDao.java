package com.nlmeetingroom.dao;

import com.nlmeetingroom.pojo.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface FloorDao extends JpaRepository<Floor,String>,JpaSpecificationExecutor<Floor>{
	public List<Floor> findByBuildingid(String builingid);
}
