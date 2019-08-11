package com.nlmeetingroom.dao;

import com.nlmeetingroom.pojo.RoomReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RoomReserveDao extends JpaRepository<RoomReserve,String>,JpaSpecificationExecutor<RoomReserve>{
    public List<RoomReserve> findByRoomidAndState(String roomid,Integer state);
	
}
