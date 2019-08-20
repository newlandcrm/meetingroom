package com.nlmeetingroom.controller;
import java.util.Date;
import java.util.Map;

import com.nlmeetingroom.service.RoomReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nlmeetingroom.pojo.RoomReserve;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.DateUtil;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/roomReserve")
public class RoomReserveController {

	@Autowired
	private RoomReserveService roomReserveService;
	/**
	 * 获取某日时间某会议室的预约情况
	 */
	@RequestMapping(value = "/roomReserveDayInfo",method = RequestMethod.POST)
	public Result roomReserveDayInfo(@RequestBody Map searchMap) throws Exception {
		String time = (String) searchMap.get("time");
		String roomid = (String) searchMap.get("roomid");
		Date day = DateUtil.transferDateFormat(time);
		return new Result(true,StatusCode.OK,"查询成功",roomReserveService.dayInfo(roomid,day));
	}

	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",roomReserveService.findAll());
	}
	/**
	 * 各个会议室历史预定次数
	 * @return
	 */
	@RequestMapping(value = "hisReserveCount",method= RequestMethod.GET)
	public Result hisReserveCount(){
		return new Result(true,StatusCode.OK,"查询成功",roomReserveService.hisReserveCount());
	}

	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",roomReserveService.findById(id));
	}

	/**
	 * 审核
	 */
	@RequestMapping(value="/examine",method = RequestMethod.POST)
	public Result examine( @RequestBody Map searchMap) throws Exception {
		roomReserveService.examine(searchMap);
		return new Result(true,StatusCode.OK,"操作成功");
	}
	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<RoomReserve> pageList = roomReserveService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<RoomReserve>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",roomReserveService.findSearch(searchMap));
    }
	/**
	 * 预定会议室
	 */
	@RequestMapping(value="/reserve",method = RequestMethod.POST)
	public Result reserve( @RequestBody Map searchMap) throws Exception {
		roomReserveService.reserve(searchMap);
		return new Result(true,StatusCode.OK,"预约成功");
	}
	/**
	 * 增加
	 * @param roomReserve
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody RoomReserve roomReserve  ){
		roomReserveService.add(roomReserve);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param roomReserve
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody RoomReserve roomReserve, @PathVariable String id ){
		roomReserve.setId(id);
		roomReserveService.update(roomReserve);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		roomReserveService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
