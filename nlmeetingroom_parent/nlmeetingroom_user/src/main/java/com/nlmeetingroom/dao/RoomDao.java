package com.nlmeetingroom.dao;

import com.nlmeetingroom.pojo.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RoomDao extends JpaRepository<Room,String>,JpaSpecificationExecutor<Room>{


}
