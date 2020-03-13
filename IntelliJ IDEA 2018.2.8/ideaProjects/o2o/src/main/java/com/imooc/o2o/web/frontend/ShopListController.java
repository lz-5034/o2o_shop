package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LZ
 * @date 2020/3/9 10:05:17
 * @description
 */
@Controller("shopListController")
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;

    /**
     * 获取商品列表里的shopCategory列表（一级或二级），以及区域信息列表
     * @param request
     * @return
     */
    @RequestMapping("/listshopspageinfo")
    @ResponseBody
    private Map<String, Object> getShopCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //试着从前端请求中获取parentId
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        List<Area> areaList = null;
        try {
            //获取区域列表信息
            areaList = areaService.getAllArea();
            //获取店铺分类列表信息
            if (parentId != -1) {
                //如果parentId存在，则取出一级shopCategory下的二级shopCategory列表
                ShopCategory shopCategory = new ShopCategory(); //作为查询条件的 店铺分类 类
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategory.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategory(shopCategory);
            } else {
                //parentId 不存在的情况，则取出所有一级shopCategory列表
                shopCategoryList = shopCategoryService.getShopCategory(null);
            }
            if (shopCategoryList != null && shopCategoryList.size() > 0 && areaList != null && areaList.size() > 0) {
                modelMap.put("success", true);
                modelMap.put("shopCategoryList", shopCategoryList);
                modelMap.put("areaList", areaList);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.toString());
        }
        return modelMap;
    }

    /**
     * 获取指定查询条件下的店铺列表
     * @param request
     * @return
     */
    @RequestMapping("/listshops")
    @ResponseBody
    private Map<String, Object> shopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            //获取页码
            int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
            //获取一页需要显示的数据条数
            int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
            if (pageIndex > 0 && pageSize > 0) {
                //尝试获取一级类别id
                long parentId = HttpServletRequestUtil.getLong(request, "parentId");
                //尝试获取特点二级类别id
                long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
                //尝试获取区域id
                int areaId = HttpServletRequestUtil.getInt(request, "areaId");
                //尝试获取模糊查询的名字
                String shopName = HttpServletRequestUtil.getString(request, "shopName");
                //获取组合之后的查询条件
                Shop shopCondition = combinedQueryConditions(parentId, shopCategoryId, areaId, shopName);
                //根据查询条件和分页信息返回店铺列表
                ShopExecution shopExecution = shopService.getShopList(shopCondition, pageIndex, pageSize);
                if (shopExecution != null && shopExecution.getShopList() != null && shopExecution.getShopList().size() > 0) {
                    modelMap.put("success", true);
                    modelMap.put("shopList", shopExecution.getShopList());
                    modelMap.put("count", shopExecution.getCount());
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("msg", "empty pageSize or pageIndex");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.toString());
        }
        return modelMap;
    }

    /**
     * 将查询条件组合到shop类的方法
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */
    private Shop combinedQueryConditions(long parentId, long shopCategoryId, int areaId, String shopName) {
        //创建shop类，作为条件查询的条件载体
        Shop shop = new Shop();
        if (parentId != -1L) {
            //最终执行查询：某个一级店铺分类下的所有二级分类的店铺列表
            ShopCategory parent = new ShopCategory();
            parent.setShopCategoryId(parentId);
            ShopCategory child = new ShopCategory();
            child.setParent(parent);
            shop.setShopCategory(child);
        }
        if (shopCategoryId != -1L) {
            //最终执行查询：某个具体二级店铺分类下的店铺列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shop.setShopCategory(shopCategory);
        }
        if (areaId != -1) {
            Area area = new Area();
            area.setAreaId(areaId);
            shop.setArea(area);
        }
        if (shopName != null && !shopName.equals("")) {
            shop.setShopName(shopName);
        }
        //向前端展示的是审核通过的店铺
        shop.setEnableStatus(1);
        return shop;
    }
}
