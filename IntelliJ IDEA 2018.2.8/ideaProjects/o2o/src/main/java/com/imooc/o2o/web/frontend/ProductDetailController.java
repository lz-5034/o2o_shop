package com.imooc.o2o.web.frontend;

import com.imooc.o2o.entity.Product;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LZ
 * @date 2020/3/10 16:12:35
 * @description
 */
@Controller("productDetailController")
@RequestMapping("/frontend")
public class ProductDetailController {
    @Autowired
    private ProductService productService;

    /**
     * 根据前台的productId返回店铺信息，包括详情图片信息
     * @param request
     * @return
     */
    @RequestMapping("/listproductdetailpageinfo")
    @ResponseBody
    private Map<String, Object> listProductDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            long productId = HttpServletRequestUtil.getLong(request, "productId");
            if (productId != -1) {
                Product product = productService.getProductById(productId);
                modelMap.put("success", true);
                modelMap.put("product", product);
            } else {
                modelMap.put("success", false);
                modelMap.put("msg", "empty productId");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.toString());
        }
        return modelMap;
    }
}
