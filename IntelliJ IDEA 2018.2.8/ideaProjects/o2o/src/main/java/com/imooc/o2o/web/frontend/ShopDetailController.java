package com.imooc.o2o.web.frontend;

import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
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
 * @date 2020/3/10 18:18:32
 * @description
 */
@Controller("shopDetailController")
@RequestMapping("/frontend")
public class ShopDetailController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取店铺信息以及该店铺下的商品类别列表
     * @param request
     * @return
     */
    @RequestMapping("/listshopdetailpageinfo")
    @ResponseBody
    private Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            long shopId = HttpServletRequestUtil.getLong(request, "shopId");
            if (shopId != -1) {
                //获取指定店铺的所有信息
                Shop shop = shopService.getByShopId(shopId);
                //获取指定店铺下的所有商品分类
                List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(shopId);
                if (shop != null && productCategoryList != null && productCategoryList.size() > 0) {
                    modelMap.put("success", true);
                    modelMap.put("shop", shop);
                    modelMap.put("productCategoryList", productCategoryList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("msg", "get shop and shopCategoryList error");
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("msg", "empty shopId");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.toString());
        }
        return modelMap;
    }

    /**
     * 根据查询条件分页列出该店铺下面的所有商品
     * @param request
     * @return
     */
    @RequestMapping("/listproductsbyshop")
    @ResponseBody
    private Map<String, Object> listProductsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            //获取页码
            int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
            //获取一页需要显示的数据条数
            int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
            //获取店铺id
            long shopId = HttpServletRequestUtil.getLong(request, "shopId");
            if (pageIndex > 0 && pageSize > 0 && shopId > 0) {
                //尝试获取商品类别id
                long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
                //尝试获取模糊查询的商品名
                String productName = HttpServletRequestUtil.getString(request, "productName");
                //组合查询条件
                Product productCondition = combinedQueryConditions(shopId, productCategoryId, productName);
                //根据查询条件进行查询
                ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
                if (pe != null && pe.getProductList() != null && pe.getProductList().size() > 0) {
                    modelMap.put("success", true);
                    modelMap.put("productList", pe.getProductList());
                    modelMap.put("count", pe.getCount());
                } else {
                    modelMap.put("success", false);
                    modelMap.put("msg", "get productList inner error");
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("msg", "empty pageIndex or pageSize or shopId");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.toString());
        }
        return modelMap;
    }

    /**
     * 将查询条件组合到product类的方法
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product combinedQueryConditions(long shopId, long productCategoryId, String productName) {
        //创建product类，作为查询条件的条件载体
        Product product = new Product();
        //给商品赋值对应的店铺id
        Shop shop = new Shop();
        shop.setShopId(shopId);
        product.setShop(shop);
        if (productCategoryId != -1) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            product.setProductCategory(productCategory);
        }
        if (productName != null && !productName.equals("")) {
            product.setProductName(productName);
        }
        //只显示状态为上架的商品
        product.setEnableStatus(1);
        return product;
    }
}
