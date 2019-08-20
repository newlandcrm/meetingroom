package com.nlmeetingroom.service;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.MessagingException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.nlmeetingroom.dao.RelatePersonDao;
import com.nlmeetingroom.dao.RoomDao;
import com.nlmeetingroom.dao.UserDao;
import com.nlmeetingroom.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.DateUtil;
import util.IdWorker;

import com.nlmeetingroom.dao.RoomReserveDao;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class RoomReserveService {

    @Autowired
    private RoomReserveDao roomReserveDao;
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RelatePersonDao relatePersonDao;

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 预约
     * @param searchMap
     */
    public void reserve(Map searchMap) throws Exception {
        String userid = (String) searchMap.get("userid");
        String roomid = (String) searchMap.get("roomid");
        String content = (String) searchMap.get("content");
        ArrayList<String> arrayList= (ArrayList) searchMap.get("time");
        Date startTime = DateUtil.transferDateFormat(arrayList.get(0));
        Date endTime = DateUtil.transferDateFormat(arrayList.get(1));
        if(StringUtils.isEmpty(userid))
            throw new Exception("请先登录");
        try {
            User userTemp = userDao.findById(userid).get();
        }catch (Exception e) {
            throw new Exception("该账号有错");
        }

        RoomReserve roomReserve=new RoomReserve();
        roomReserve.setId(idWorker.nextId()+"");
        roomReserve.setState(RoomReserve.EXAMINE_STATE_NOT_EXAMINE);
        roomReserve.setContent(content);
        roomReserve.setStartdate(startTime);
        roomReserve.setEnddate(endTime);
        Room room = roomDao.findById(roomid).get();
        if(room.getOpenstate()==0){
            throw new Exception("无法预约：该会议室暂时不开放");
        }
        User user=new User();
        user.setId(userid);
        roomReserve.setRoom(room);
        roomReserve.setUser(user);
        check(roomReserve);
        roomReserveDao.save(roomReserve);
    }


    /**
     * 审核
     *
     * @param searchMap
     */
    public void examine(Map searchMap) throws Exception {
        String rsId = (String) searchMap.get("rsid");
        Integer flag = (Integer) searchMap.get("flag");
        String message = (String) searchMap.get("message");
        RoomReserve roomReserve = roomReserveDao.findById(rsId).get();
        if(flag == RoomReserve.EXAMINE_STATE_SUCCESS)
          check(roomReserve);
        roomReserve.setState(flag);
        roomReserveDao.save(roomReserve);
        if (flag == RoomReserve.EXAMINE_STATE_SUCCESS) {
            message = roomReserve.getRoom().getName() + "：通过审核";
            sendEmail(message, roomReserve.getUser().getId());
        } else if (flag == RoomReserve.EXAMINE_STATE_FAIL) {
            message = roomReserve.getRoom().getName() + "：未通过审核"+"   "+message;
            sendEmail(message, roomReserve.getUser().getId());
        }

    }

    private void check(RoomReserve roomReserve) throws Exception {
        Date curentDate = new Date();
        System.out.println(curentDate);
        if (roomReserve.getEnddate().getTime()<curentDate.getTime()){
            throw new Exception("预约时间有误：当前时间已超过预约的结束时间");
        }
        if (roomReserve.getStartdate().getTime()<curentDate.getTime()){
            throw new Exception("预约时间有误：当前时间已超过预约的开始时间");
        }
        List<RoomReserve> roomReserves = roomReserveDao.findByRoomidAndState(roomReserve.getRoom().getId(), RoomReserve.EXAMINE_STATE_SUCCESS);
        for (RoomReserve reserve : roomReserves) {
            boolean checkResult = checkTime(roomReserve.getStartdate(), roomReserve.getEnddate(), reserve.getStartdate(), reserve.getEnddate());
            if(checkResult){
                throw new Exception("该时间段与他人已预约的时间有冲突！");
            }
        }
    }

    public static boolean checkTime(Date leftStartDate, Date leftEndDate, Date rightStartDate, Date rightEndDate) {

        return
        ((leftStartDate.getTime() >= rightStartDate.getTime())
                && leftStartDate.getTime() < rightEndDate.getTime())
                ||
                ((leftStartDate.getTime() > rightStartDate.getTime())
                        && leftStartDate.getTime() <= rightEndDate.getTime())
                ||
                ((rightStartDate.getTime() >= leftStartDate.getTime())
                        && rightStartDate.getTime() < leftEndDate.getTime())
                ||
                ((rightStartDate.getTime() > leftStartDate.getTime())
                        && rightStartDate.getTime() <= leftEndDate.getTime());

    }

    private void sendEmail(String message, String userid) throws MessagingException {
        User user = userDao.findById(userid).get();
        if (user.getEmail() != null && !user.getEmail().equals("")) {
            System.out.println("send begin");
            Map<String,String> map=new HashMap();
            map.put("toEmail",user.getEmail());
            map.put("message",message);
            rabbitTemplate.convertAndSend("email",map);
            System.out.println("send end");
        }
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<RoomReserve> findAll() {
        return roomReserveDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<RoomReserve> findSearch(Map whereMap, int page, int size) {
        Specification<RoomReserve> specification = createSpecification(whereMap);
        Sort sort = new Sort(Sort.Direction.ASC, "state","startdate");
        PageRequest pageRequest = PageRequest.of(page - 1, size,sort);
        return roomReserveDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<RoomReserve> findSearch(Map whereMap) {
        Specification<RoomReserve> specification = createSpecification(whereMap);
        return roomReserveDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public RoomReserve findById(String id) {
        return roomReserveDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param roomReserve
     */
    public void add(RoomReserve roomReserve) {
        roomReserve.setId(idWorker.nextId() + "");
        roomReserveDao.save(roomReserve);
    }

    /**
     * 修改
     *
     * @param roomReserve
     */
    public void update(RoomReserve roomReserve) {
        roomReserveDao.save(roomReserve);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        relatePersonDao.deleteByreserveid(id);
        roomReserveDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<RoomReserve> createSpecification(Map searchMap) {

        return new Specification<RoomReserve>() {

            @Override
            public Predicate toPredicate(Root<RoomReserve> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 申请人id
                if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
                }
                // 会议室id
                if (searchMap.get("roomid") != null && !"".equals(searchMap.get("roomid"))) {
                    predicateList.add(cb.like(root.get("roomid").as(String.class), "%" + (String) searchMap.get("roomid") + "%"));
                }
                // 内容
                if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                    predicateList.add(cb.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
                }
                // 审核状态
                if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.equal(root.get("state").as(Integer.class), searchMap.get("state")));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

    /**
     * 获取某日时间某会议室的预约情况
     * @param
     */
    public List<RoomReserve> dayInfo(String roomid, Date day) throws Exception {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(day);
        calendar.add(calendar.DATE,1);
        Date startDate=day;
        Date endDate=calendar.getTime();
        System.out.println(format.format(startDate));
        System.out.println(format.format(endDate));
        try {
            return roomReserveDao.oneDayInfo(roomid,format.format(startDate),format.format(endDate));
        }catch (Exception e){
            throw new Exception("系统异常");
        }

    }

    public ArrayList hisReserveCount() {
        ArrayList<RoomHisResCount> roomHisResCounts = new ArrayList<>();
        List<Room> rooms = roomService.findAll();
        for (Room room:rooms) {
            RoomHisResCount roomHisResCount = new RoomHisResCount();
            roomHisResCount.setId(room.getId());
            roomHisResCount.setName(room.getName());

            long count = roomReserveDao.hisReserveCount(room.getId());
            roomHisResCount.setCount(count);
            roomHisResCounts.add(roomHisResCount);
        }
        return roomHisResCounts;
    }
}
