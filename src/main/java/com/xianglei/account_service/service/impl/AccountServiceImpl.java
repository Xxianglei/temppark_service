package com.xianglei.account_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xianglei.account_service.common.utils.Tools;
import com.xianglei.account_service.domain.BsOrder;
import com.xianglei.account_service.domain.BsPark;
import com.xianglei.account_service.domain.BsUser;
import com.xianglei.account_service.mapper.OrderMapper;
import com.xianglei.account_service.mapper.ParkMapper;
import com.xianglei.account_service.mapper.UserMapper;
import com.xianglei.account_service.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 计费管理service
 */
@Service
public class AccountServiceImpl implements AccountService {
    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ParkMapper parkMapper;

    @Override
    public Double getPriceById(String orderId) throws RuntimeException {
        Double result = 0.0;
        // 查询订单是否存在
        BsOrder bsOrder = orderMapper.selectById(orderId);
        if (Tools.isNull(bsOrder)) {
            logger.error("订单不存在");
            throw new RuntimeException("订单不存在");
        } else {
            result = computePrice(bsOrder);
        }
        return result;
    }

    @Override
    public Double computePrice(BsOrder bsOrder) throws RuntimeException {
        Double price = new Double(18.0);
        String userId = bsOrder.getUserId();
        BsUser user = userMapper.selectOne(new QueryWrapper<BsUser>().eq("FLOW_ID", userId));
        if (Tools.isNull(user)) {
            logger.error("用户不存在");
            throw new RuntimeException("用户不存在");
        } else {
            String vip = user.getVip();
            price = computePriceCount(bsOrder, vip);
        }
        return price;
    }

    @Override
    public Double computePriceCount(BsOrder bsOrder, String vip) throws RuntimeException {
        BsPark park = parkMapper.selectOne(new QueryWrapper<BsPark>().eq("FLOW_ID", bsOrder.getParkId()));
        // 1 或者 null 是非会员
        if (vip == null || vip.equals("1")) {
            // 1或null   是夜间
            Double aDouble;
            String evening = bsOrder.getEvening();
            if (evening == null || evening.equals("1")) {
                aDouble = park.getyPrice();
            } else {
                aDouble = park.getbPrice();
            }
            return aDouble;
        } else {
            // 是vip
            Double aDouble;
            Double zhekou = park.getvPrice();
            String evening = bsOrder.getEvening();
            if (evening == null || evening.equals("1")) {
                aDouble = park.getyPrice();
            } else {
                aDouble = park.getbPrice();
            }
            // 打折后价格
            aDouble = aDouble * zhekou;
            return aDouble;
        }
    }

    @Override
    public int updateParkPrice(Double bPrice, Double yPrice, Double vPrice, String parkId) {
        int result = 0;
        BsPark park = parkMapper.selectById(parkId);
        park.setbPrice(bPrice);
        park.setyPrice(yPrice);
        park.setvPrice(vPrice);
        result = parkMapper.updateById(park);
        return result;
    }

    @Override
    public BsPark queryParkPrice(String parkId) {
        BsPark park = parkMapper.selectById(parkId);
        return park;
    }
}
