package com.imooc.o2o.interceptor;

import com.imooc.o2o.entity.Shop;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author LZ
 * @date 2020/3/12 18:54:01
 * @description
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取当前操作的店铺
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //获取该店家能够操作的店铺列表
        List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
        if (currentShop != null && shopList != null) {
            //若能够操作的店铺列表包含当前操作的店铺，则放行
            for (Shop shop : shopList) {
                if (shop.getShopId() == currentShop.getShopId()) {
                    return true;
                }
            }
        }
        return false;
    }
}
