package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LZ
 * @date 2020/2/22 21:02:32
 * @description 后台店铺路由器
 */
@Controller("shopAdminController")
@RequestMapping("/shop_admin")
public class ShopAdminController {
    /**
     * 添加和修改店铺显示的页面
     * @return
     */
    @RequestMapping("/shop_operation")
    public String shopOperation() {
        return "shop/shop_operation";
    }

    /**
     * 显示所有店铺的页面
     * @return
     */
    @RequestMapping("/shop_list")
    public String shopList() {
        return "shop/shop_list";
    }

    /**
     * 店铺管理页面
     * @return
     */
    @RequestMapping("/shop_management")
    public String shopManagement() {
        return "shop/shop_management";
    }

    /**
     * 商品分类管理页面
     * @return
     */
    @RequestMapping("/product_category_management")
    public String productCategoryManagement() {
        return "shop/productCategory_management";
    }

    /**
     * 商品管理显示的页面
     * @return
     */
    @RequestMapping("/product_management")
    public String productManagement() {
        return "shop/product_management";
    }

    /**
     * 添加和修改商品显示的页面
     * @return
     */
    @RequestMapping("/product_operation")
    public String productOperation() {
        return "shop/product_operation";
    }
}
