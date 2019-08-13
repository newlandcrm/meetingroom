package com.nlmeetingroom.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nlmeetingroom.pojo.Building;
import com.nlmeetingroom.service.BuildingService;

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
@RequestMapping("/building")
public class BuildingController {

	@Autowired
	private BuildingService buildingService;




	/**
	 * 测试
	 * @param searchMap
	 * @return
	 */
	@RequestMapping(value="/test",method = RequestMethod.POST)
	public Result test( @RequestBody Map searchMap){
		System.out.println((ArrayList) searchMap.get("starttime"));
		ArrayList<String> arrayList= (ArrayList) searchMap.get("starttime");
		System.out.println(arrayList.get(0));
		Date date = DateUtil.transferDateFormat(arrayList.get(0));
		System.out.println(date.getTime());
		Date date1 = DateUtil.transferDateFormat(arrayList.get(1));
		System.out.println(date.getTime());
		return new Result(true,StatusCode.OK,"查询成功");
	}
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",buildingService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",buildingService.findById(id));
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
		Page<Building> pageList = buildingService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Building>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",buildingService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param building
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Building building  ){
		buildingService.add(building);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param building
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Building building, @PathVariable String id ){
		building.setId(id);
		buildingService.update(building);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		buildingService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value ="/getChildren" ,method= RequestMethod.GET)
	public Result getChildren(){

		return new Result(true,StatusCode.OK,"查询成功",buildingService.getChildren());
	}
}
