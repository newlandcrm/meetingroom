package com.nlmeetingroom.controller;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nlmeetingroom.pojo.User;
import com.nlmeetingroom.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	JwtUtil jwtUtil;

	/**
	 * 修改密码
	 * @param
	 */
	@RequestMapping(value="/password",method= RequestMethod.PUT)
	public Result updatePassword(@RequestBody Map searchMap){
		System.out.println(searchMap);
		userService.updatePassword(searchMap);

		return new Result(true,StatusCode.OK,"修改成功");
	}
	/**
	 * 用户登陆

	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Result login(@RequestBody Map<String,String> loginMap){
		User user = userService.loginByUsernameAndPassword(loginMap.get("username"),loginMap.get("password"),"0");
		if(user!=null){
			String token = jwtUtil.createJWT(user.getId(),user.getNickname(), "user");
			Map map=new HashMap();
			map.put("token",token);
			map.put("userid",user.getId());
			map.put("nickname",user.getNickname());//昵称
			map.put("avatar",user.getAvatar());//头像
			return new Result(true,StatusCode.OK,"登陆成功",map);
		}else{
			return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
		}
	}

	/**
	 * 管理员登陆

	 * @return
	 */

	@RequestMapping(value="/admin/login",method=RequestMethod.POST)
	public Result adminLogin(@RequestBody Map<String,String> loginMap){
		User user = userService.loginByUsernameAndPassword(loginMap.get("username"),loginMap.get("password"),"1");
		if(user!=null){
			String token = jwtUtil.createJWT(user.getId(),user.getNickname(), "admin");
			Map map=new HashMap();
			map.put("token",token);
			map.put("id",user.getId());
			map.put("name",user.getNickname());//昵称
			map.put("avatar",user.getAvatar());//头像
			return new Result(true,StatusCode.OK,"登陆成功",map);
		}else{
			return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
		}
	}

	@RequestMapping(value="/info",method=RequestMethod.POST)
	public Result getInfo(@RequestBody Map<String,String> getInfoMap) throws Exception {
		String token = getInfoMap.get("token");
		try {
			Claims claims = jwtUtil.parseJWT(token);
			if(claims==null){
				return new Result(false,StatusCode.ACCESSERROR,"权限不足");
			}
			String id = claims.getId();
			User user = userService.findById(id);
			System.out.println(user.toString());
			return new Result(true,StatusCode.OK,"success",user);
		}catch (Exception e){
			throw new Exception("验证失败,请重新登录");
		}



	}
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public Result register( @RequestBody User user ) throws Exception {
		userService.add(user);
		return new Result(true,StatusCode.OK,"注册成功");
	}


	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
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
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	

	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
