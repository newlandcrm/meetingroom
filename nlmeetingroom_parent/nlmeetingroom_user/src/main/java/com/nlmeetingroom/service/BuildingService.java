package com.nlmeetingroom.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nlmeetingroom.dao.FloorDao;
import com.nlmeetingroom.pojo.Building;
import com.nlmeetingroom.pojo.Children;
import com.nlmeetingroom.pojo.Floor;
import com.nlmeetingroom.pojo.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.nlmeetingroom.dao.BuildingDao;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class BuildingService {

	@Autowired
	private BuildingDao buildingDao;
	@Autowired
	private FloorDao floorDao;

	@Autowired
	private FloorService floorService;
	@Autowired
	private RoomService roomService;
	@Autowired
	private IdWorker idWorker;
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Building> findAll() {

		List<Building> buildingList = buildingDao.findAll();
		relateFloor(buildingList);
		return buildingList;
	}

	private void relateFloor(List<Building> buildingList) {
		for (Building building:buildingList) {
			List<Floor> floors = floorDao.findByBuildingid(building.getId());
			building.setFloors(floors);
		}
	}


	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Building> findSearch(Map whereMap, int page, int size) {
		Specification<Building> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return buildingDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Building> findSearch(Map whereMap) {
		Specification<Building> specification = createSpecification(whereMap);
		return buildingDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Building findById(String id) {
		Building building = buildingDao.findById(id).get();
		relateFloor(building);
		return building;
	}

	private void relateFloor(Building building) {
			List<Floor> floors = floorDao.findByBuildingid(building.getId());
			building.setFloors(floors);
	}
	/**
	 * 增加
	 * @param building
	 */
	public void add(Building building) {
		building.setId( idWorker.nextId()+"" );
		buildingDao.save(building);
	}

	/**
	 * 修改
	 * @param building
	 */
	public void update(Building building) {
		buildingDao.save(building);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		buildingDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Building> createSpecification(Map searchMap) {

		return new Specification<Building>() {

			@Override
			public Predicate toPredicate(Root<Building> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 楼名
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }

				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

    public Map getChildren() throws IOException {
		String children = (String) redisTemplate.opsForValue().get("indexChildren");
		if(children!=null){
			System.out.println("从redis获取");
			ObjectMapper objectMapper = new ObjectMapper();
			Map map = objectMapper.readValue(children, Map.class);
			return map;
		}
		Map map=new HashMap();
		List<Building> allBuildings = findAll();
		List<Children> firstChildrens=new ArrayList<>();
		for (Building building:allBuildings) {
			Children firstChildren=new Children();
			firstChildren.setId(building.getId());
			firstChildren.setName(building.getName());
			int firstAssertNum=0;
			//floor
			Map firstMap=new HashMap();
			firstMap.put("buildingid",building.getId());
			List<Floor> floors = floorService.findSearch(firstMap);
			List<Children> secondChildrens=new ArrayList<>();
			for (Floor floor:floors) {
				Children secondChildren=new Children();
				secondChildren.setId(floor.getId());
				secondChildren.setName(floor.getDescribes());
				int secondAssertNum=0;
				//room
				Map secondMap=new HashMap();
				secondMap.put("floorid",floor.getId());
				List<Room> rooms = roomService.findSearch(secondMap);
				List<Children> thirdChildrens=new ArrayList<>();
				for (Room room:rooms) {
					Children thirdChildren=new Children();
					thirdChildren.setId(room.getId());
					thirdChildren.setName(room.getName());
					thirdChildren.setAsset_num(room.getCapacity());
					thirdChildrens.add(thirdChildren);
					secondAssertNum+=room.getCapacity();
				}

				if(thirdChildrens.size()!=0)
					secondChildren.setChildren(thirdChildrens);
				//回填容量
				secondChildren.setAsset_num(secondAssertNum);
				secondChildrens.add(secondChildren);
				firstAssertNum+=secondAssertNum;
			}
			if(secondChildrens.size()!=0)
				firstChildren.setChildren(secondChildrens);

			//回填容量
			firstChildren.setAsset_num(firstAssertNum);
			firstChildrens.add(firstChildren);
		}

		map.put("children",firstChildrens);
		System.out.println("写入redis");
		ObjectMapper objectMapper = new ObjectMapper();
		String string = objectMapper.writeValueAsString(map);
		redisTemplate.opsForValue().set("indexChildren",string);
		return map;
    }
}
