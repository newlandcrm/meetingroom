package com.nlmeetingroom.dao;

import com.nlmeetingroom.pojo.RoomReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RoomReserveDao extends JpaRepository<RoomReserve,String>,JpaSpecificationExecutor<RoomReserve>{
    public List<RoomReserve> findByRoomidAndState(String roomid,Integer state);

    @Query(nativeQuery = true,value = "SELECT * FROM user_room_reserve WHERE roomid=?1 and startdate >= ?2 AND enddate< ?3")
    public List<RoomReserve> oneDayInfo(String roomid, String startDate, String endDate);

    @Query(nativeQuery = true,value = "select count(*) from user_room_reserve where roomid=?1")
    long hisReserveCount(String roomid);
}
