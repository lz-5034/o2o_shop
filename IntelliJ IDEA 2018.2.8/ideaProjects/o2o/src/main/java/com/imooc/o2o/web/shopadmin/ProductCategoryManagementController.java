package com.imooc.o2o.web.shopadmin;

import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.dto.Result;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductCategoryStatusEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LZ
 * @date 2020/2/28 16:38:56
 * @description
 */
@Controller("productCategoryManagementController")
@RequestMapping("/shop_admin")
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 根据在session域中的店铺id，获取属于该店铺下的商品分类
     * @param request
     * @return
     */
    @RequestMapping("/get_product_category_list")
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() > 0) {
            List<ProductCategory> list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true, list);
        } else {
            ProductCategoryStatusEnum statusEnum = ProductCategoryStatusEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false, statusEnum.getStatus(), statusEnum.getStatusInfo());
        }
    }

    /**
     * 批量添加商品分类
     * @param productCategoryList
     * @param request
     * @return
     */
    @RequestMapping("/add_product_category")
    @ResponseBody
    private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request) {
        Map<String, Object> modelMap = null;
        Shop currentShop = null;
        try {
            modelMap = new HashMap<>();
            currentShop = (Shop) request.getSession().getAttribute("currentShop");
            if (productCategoryList != null && productCategoryList.size() > 0) {
                for (ProductCategory productCategory : productCategoryList) {
                    productCategory.setShopId(currentShop.getShopId());
                }
                ProductCategoryExecution categoryExecution = productCategoryService.batchAddProductCategory(productCategoryList);
                if (categoryExecution.getStatus() == ProductCategoryStatusEnum.SUCCESS.getStatus()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("msg", categoryExecution.getStatusInfo());
                }
                return modelMap;
            } else {
                modelMap.put("success", false);
                modelMap.put("msg", "请至少输入一个商品类别");
                return modelMap;
            }
        } catch (ProductCategoryOperationException e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.toString());
            return modelMap;
        }
    }

    /**
     * 删除商品分类
     * @param productCategoryId
     * @param request
     * @return
     */
    @RequestMapping("/remove_product_category")
    @ResponseBody
    private Map<String, Object> removeProductCategorys(Long productCategoryId, HttpServletRequest request) {
        Map<String, Object> modelMap = null;
        Shop currentShop = null;
        try {
            modelMap = new HashMap<>();
            currentShop = (Shop)request.getSession().getAttribute("currentShop");
            if (productCategoryId != null && productCategoryId > 0) {
                ProductCategoryExecution productCategoryExecution = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (productCategoryExecution.getStatus() == ProductCategoryStatusEnum.SUCCESS.getStatus()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("msuccess", false);
                    modelMap.put("msg", productCategoryExecution.getStatusInfo());
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("msg", "请选择商品类别进行删除");
            }
            return modelMap;
        } catch (ProductCategoryOperationException e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.getMessage());
            return modelMap;
        }
    }
}
