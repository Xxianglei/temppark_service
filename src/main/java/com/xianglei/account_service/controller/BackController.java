package com.xianglei.account_service.controller;

import com.xianglei.account_service.common.BaseJson;
import com.xianglei.account_service.common.utils.Tools;
import com.xianglei.account_service.domain.BsPark;
import com.xianglei.account_service.service.AccountService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Auther: Xianglei
 * @Company: xxx
 * @Date: 2020/4/27 16:20
 * com.xianglei.account_service.controller
 * @Description: 后台接口
 */
@RestController
@RequestMapping("/back")
public class BackController {
    @Autowired
    AccountService accountService;

    // 停车场车位价格修改
    @GetMapping("/update")
    public BaseJson updateParkPrice(@RequestParam(value = "parkId") String parkId, @RequestParam Double bPrice, @RequestParam Double yPrice, @RequestParam Double vPrice) {
        BaseJson baseJson = new BaseJson();
        if (StringUtils.isEmpty(parkId)) {
            baseJson.setMessage("停车场ID不能为空");
            baseJson.setStatus(false);
            baseJson.setCode(500);
        }
        try {
            if (Tools.isNotNull(bPrice) && Tools.isNotNull(yPrice) && Tools.isNotNull(vPrice)) {
                int index = accountService.updateParkPrice(bPrice, yPrice, vPrice, parkId);
                if (index != 0) {
                    baseJson.setStatus(true);
                    baseJson.setCode(200);
                    baseJson.setMessage("价格更新成功");
                } else {
                    baseJson.setMessage("更新价格失败");
                    baseJson.setStatus(false);
                    baseJson.setCode(500);
                }
            } else {
                baseJson.setMessage("不允许传入空价格");
                baseJson.setStatus(false);
                baseJson.setCode(500);
            }

        } catch (RuntimeException e) {
            baseJson.setMessage("更新价格失败");
            baseJson.setStatus(false);
            baseJson.setCode(500);
        }

        return baseJson;
    }

    // 停车场车位价格查询
    @GetMapping("/query")
    public BaseJson queryParkPrice(@RequestParam(value = "parkId")  String parkId) {
        BaseJson baseJson = new BaseJson();
        try {
            if (StringUtils.isNotEmpty(parkId)) {
                BsPark bsPark = accountService.queryParkPrice(parkId);
                HashMap<String, Double> priceMap = new HashMap<>();
                priceMap.put("bPrice", bsPark.getbPrice());
                priceMap.put("yPrice", bsPark.getyPrice());
                priceMap.put("vPrice", bsPark.getvPrice());
                baseJson.setData(priceMap);
                baseJson.setStatus(true);
                baseJson.setCode(200);
                baseJson.setMessage("调用成功");
            } else {
                baseJson.setMessage("停车场id不能为空");
                baseJson.setStatus(false);
                baseJson.setCode(500);
            }
        } catch (RuntimeException e) {
            baseJson.setMessage("查询价格失败");
            baseJson.setStatus(false);
            baseJson.setCode(500);
        }

        return baseJson;
    }

}
