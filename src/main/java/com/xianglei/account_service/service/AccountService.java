package com.xianglei.account_service.service;

import com.xianglei.account_service.domain.BsOrder;
import com.xianglei.account_service.domain.BsPark;

import java.util.List;

/**
 * 停车场计费管理
 */
public interface AccountService {

    /**
     * 根据订单编号计算价格
     *
     * @param orderId
     * @return
     */
    Double getPriceById(String orderId) throws RuntimeException;

    /**
     * 计价方法
     *
     * @param bsOrder
     * @return
     */
    Double computePrice(BsOrder bsOrder) throws RuntimeException;

    /**
     * 计算策略详情
     *
     * @param bsOrder
     * @return
     */
    Double computePriceCount(BsOrder bsOrder, String vip) throws RuntimeException;

    /**
     * 更新价位
     *
     * @param bPrice
     * @param yPrice
     * @param vPrice
     * @return
     */
    int updateParkPrice(Double bPrice, Double yPrice, Double vPrice, String parkId);

    /**
     * 根据停车场id获取价格
     *
     * @param parkId
     * @return
     */
    BsPark queryParkPrice(String parkId);
}
