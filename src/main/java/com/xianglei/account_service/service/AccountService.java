package com.xianglei.account_service.service;

import com.xianglei.account_service.domain.BsOrder;

import java.util.List;

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
     * @param bsOrder
     * @return
     */
    Double computePrice(BsOrder bsOrder)throws RuntimeException;

    /**
     * 计算策略详情
     * @param bsOrder
     * @return
     */
    Double computePriceCount(BsOrder bsOrder,String vip)throws RuntimeException;
}
