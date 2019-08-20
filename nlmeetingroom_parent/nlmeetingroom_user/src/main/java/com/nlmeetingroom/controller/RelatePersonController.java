package com.nlmeetingroom.controller;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nlmeetingroom.pojo.RelatePerson;
import com.nlmeetingroom.service.RelatePersonService;

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
@RequestMapping("/relatePerson")
public class RelatePersonController {

	@Autowired
	private RelatePersonService relatePersonService;



	/**
	 * 批量增加
	 * @param
	 */
	@RequestMapping(value = "/addlist" ,method=RequestMethod.POST)
	public Result addList(@RequestBody Map searchMap  ){
		ArrayList<String> arrayList= (ArrayList)searchMap.get("relatePerson");
		String reserveid = (String) searchMap.get("reserveid");
		relatePersonService.addList(reserveid,arrayList);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",relatePersonService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",relatePersonService.findById(id));
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
		Page<RelatePerson> pageList = relatePersonService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<RelatePerson>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",relatePersonService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param relatePerson
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody RelatePerson relatePerson  ){
		relatePersonService.add(relatePerson);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param relatePerson
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody RelatePerson relatePerson, @PathVariable String id ){
		relatePerson.setId(id);
		relatePersonService.update(relatePerson);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		relatePersonService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
