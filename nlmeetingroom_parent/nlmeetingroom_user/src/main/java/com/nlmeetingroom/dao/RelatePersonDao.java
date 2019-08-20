package com.nlmeetingroom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.nlmeetingroom.pojo.RelatePerson;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
@Transactional
public interface RelatePersonDao extends JpaRepository<RelatePerson,String>,JpaSpecificationExecutor<RelatePerson>{
    @Modifying
    @Query(nativeQuery = true,value = "delete from reserve_relate_person where reserveid=?1")
    public void deleteByreserveid(String reserveid);
}
