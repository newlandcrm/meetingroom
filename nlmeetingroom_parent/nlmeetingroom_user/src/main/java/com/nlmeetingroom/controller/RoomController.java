package com.nlmeetingroom.controller;
import java.util.Map;

import com.nlmeetingroom.pojo.Floor;
import com.nlmeetingroom.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nlmeetingroom.pojo.Room;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/room")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",roomService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",roomService.findById(id));
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
		Page<Room> pageList = roomService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Room>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",roomService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param room
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Room room  ){
		System.out.println(room.toString());
		Floor floor=new Floor();
		floor.setId(room.getFloorid());
		room.setFloor(floor);
		roomService.add(room);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param room
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Room room, @PathVariable String id ){
		room.setId(id);
		Floor floor=new Floor();
		floor.setId(room.getFloorid());
		room.setFloor(floor);
		roomService.update(room);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		roomService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
