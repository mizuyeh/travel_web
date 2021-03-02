package com.cyx.service.impl;

import com.cyx.dao.*;
import com.cyx.entity.Member;
import com.cyx.entity.Order;
import com.cyx.entity.OrderAdd;
import com.cyx.entity.Traveler;
import com.cyx.service.OrderService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @date 2021/2/27
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private TravelerDao travelerDao;

    @Autowired
    private OrderTravelerDao orderTravelerDao;

    @Override
    public List<Order> findAll(int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        return orderDao.findAll();
    }

    @Override
    public Order findById(String id) throws Exception {
        return orderDao.findById(id);
    }

    @Override
    public int delete(String orderNum) throws Exception {
        return orderDao.delete(orderNum);
    }

    @Override
    public int updateForeignKeyCheck(int status) throws Exception {
        return orderDao.updateForeignKeyCheck(status);
    }

    @Override
    public int save(OrderAdd orderAdd) throws Exception {
        //1.保存product
        productDao.saveProduct(orderAdd.getProduct());
        //2.保存member
        memberDao.save(orderAdd.getMember());
        //3.保存旅客,保存完取出对应id
        List<String> travelerIds = new ArrayList<>();
        for(Traveler traveler : orderAdd.getTravelers()) {
            travelerDao.save(traveler);
            String id = travelerDao.findId(traveler.getTravelerName());
            travelerIds.add(id);
        }
        //4.保存order之前需要先获取productId和memberId
        String productId = productDao.findId(orderAdd.getProduct().getProductNum());
        String memberId = memberDao.findId(orderAdd.getMember().getMemberName());
        Order order = orderAdd.getOrder();
        order.setProductId(productId);
        order.setMemberId(memberId);
        orderDao.save(order);
        //5.保存order_traveler表
        String orderId = orderDao.findId(order.getOrderNum());
        for (String travelerId : travelerIds) {
            orderTravelerDao.save(orderId, travelerId);
        }
        return 1;
    }
}
