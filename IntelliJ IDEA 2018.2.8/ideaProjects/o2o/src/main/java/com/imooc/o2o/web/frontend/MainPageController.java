package com.imooc.o2o.web.frontend;

import com.imooc.o2o.entity.HeadLine;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.HeadLineService;
import com.imooc.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LZ
 * @date 2020/3/4 11:50:45
 * @description
 */
@Controller("mainPageController")
@RequestMapping("/frontend")
public class MainPageController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;

    /**
     * 初始化前端首页内容，包括一级店铺类别和头条
     * @return
     */
    @RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> mainPageInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            //获取一级店铺类别
            List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategory(null);
            //获取状态为（1）可用的头条
            HeadLine headLine = new HeadLine();
            headLine.setEnableStatus(1);
            List<HeadLine> headLineList = headLineService.getHeadLineList(headLine);
            if (shopCategoryList != null && shopCategoryList.size() > 0 && headLineList != null && headLineList.size() > 0) {
                modelMap.put("success", true);
                modelMap.put("shopCategoryList", shopCategoryList);
                modelMap.put("headLineList", headLineList);
            } else {
                modelMap.put("success", false);
                modelMap.put("msg", "初始化首页信息失败！");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.toString());
        }
        return modelMap;
    }
}
