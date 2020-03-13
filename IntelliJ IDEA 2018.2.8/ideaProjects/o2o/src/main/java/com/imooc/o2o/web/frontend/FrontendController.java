package com.imooc.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LZ
 * @date 2020/3/4 21:40:31
 * @description 前台页面路由器
 */
@Controller("frontendController")
@RequestMapping("/frontend")
public class FrontendController {

    //首页路由
    @RequestMapping("/index")
    private String index() {
        return "/frontend/index";
    }

    //店铺列表路由
    @RequestMapping("/shoplist")
    private String shopList() {
        return "/frontend/shoplist";
    }

    //店铺详情页路由
    @RequestMapping("/shopdetail")
    private String showShopDetail() {
        return "/frontend/shopdetail";
    }

    //商品详情页路由
    @RequestMapping("/productdetail")
    private String showProductDetail() {
        return "/frontend/productdetail";
    }
}
