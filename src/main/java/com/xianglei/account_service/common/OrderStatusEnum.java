package com.xianglei.account_service.common;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/17 14:35
 * com.xianglei.account_service.common
 * @Description:订单状态枚举类
 */
public enum OrderStatusEnum {

    NO_PAY("未支付"),
    PAYED("已支付"),
    EXPIRE("已过期");
    private final String name;

    OrderStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
