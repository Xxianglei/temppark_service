package com.xianglei.account_service.controller;

import com.xianglei.account_service.common.BaseJson;
import com.xianglei.account_service.domain.BsOrder;
import com.xianglei.account_service.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/27 15:38
 * com.xianglei.account_service.controller
 * @Description: 暴露给app使用的计费计算api
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    AccountService accountService;

    @GetMapping("/getPrice")
    public BaseJson getPriceByOrder(@RequestParam String orderId) {
        BaseJson baseJson = new BaseJson();
        try {
            if (StringUtils.isNotEmpty(orderId)) {
                Double price = accountService.getPriceById(orderId);
                baseJson.setData(price);
                baseJson.setStatus(true);
                baseJson.setCode(200);
                baseJson.setMessage("调用成功");
            } else {
                baseJson.setMessage("调用失败:订单编号为空");
                baseJson.setStatus(false);
                baseJson.setCode(500);
            }
        }catch (RuntimeException e){
            baseJson.setMessage("调用失败:内部异常");
            baseJson.setStatus(false);
            baseJson.setCode(500);
        }

        return baseJson;
    }
}
