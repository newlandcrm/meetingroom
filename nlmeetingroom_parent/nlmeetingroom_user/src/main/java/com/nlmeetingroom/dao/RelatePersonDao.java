package com.nlmeetingroom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nlmeetingroom.pojo.RelatePerson;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RelatePersonDao extends JpaRepository<RelatePerson,String>,JpaSpecificationExecutor<RelatePerson>{

    public void deleteByReserveid(String reserveid);
}
