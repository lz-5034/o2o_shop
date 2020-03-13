package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStatusEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.utils.HttpServletRequestUtil;
import com.imooc.o2o.utils.VerifyCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LZ
 * @date 2020/3/1 17:01:07
 * @description
 */
@Controller("productManagementController")
@RequestMapping("/shop_admin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 添加商品功能
     * @param request
     * @return
     */
    @RequestMapping("/product_add")
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //校验验证码：
        if (!VerifyCodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("msg", "验证码错误");
            return modelMap;
        }
        //处理图片文件相关：
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartFile thumbnailFile = null;
        ImageHolder thumbnail = null;
        CommonsMultipartFile productImgFile = null;
        ImageHolder productImage = null;
        //创建文件解析器
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //判断当前请求中是否存在文件流
            if (multipartResolver.isMultipart(request)) {
                //强转request，使其强化
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                //操作缩略图
                thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                //操作详情图
                for (int i = 0; i < 6; i++) {   //最多支持上传6张详情图
                    productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                    if (productImgFile == null) {
                        break;
                    }
                    productImage = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                    productImgList.add(productImage);
                }
            } else { //没有存在文件流，直接返回错误
                modelMap.put("success", false);
                modelMap.put("msg", "上传图片不能为空！");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.toString());
            return modelMap;
        }

        //处理商品表单普通数据：
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.toString());
            return modelMap;
        }

        //最后的添加操作：
        try {
            if (product != null && thumbnail != null && productImgList.size() > 0) {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ProductExecution productExecution = productService.addProduct(thumbnail, productImgList, product);
                if (productExecution.getStatus() == ProductStatusEnum.SUCCESS.getStatus()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("msg", productExecution.getStatusInfo());
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("msg", "请输入商品信息");
            }
        } catch (ProductOperationException e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.toString());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 根据商品id获取商品和商品分类信息
     * @param productId
     * @return
     */
    @RequestMapping("/get_product_by_id")
    @ResponseBody
    private Map<String, Object> getProductById(Long productId) {
        Map<String, Object> modelMap = new HashMap<>();
        //非空判断
        if (productId != null && productId > 0) {
            //获取商品信息
            Product product = productService.getProductById(productId);
            //获取商品分类信息
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("msg", "empty productId");
        }
        return modelMap;
    }

    /**
     * 修改商品功能
     * @param request
     * @return
     */
    @RequestMapping("/product_modify")
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断是否是上下架操作
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        //非上下架操作时，则校验验证码：
        if (!statusChange && !VerifyCodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("msg", "验证码错误");
            return modelMap;
        }
        //处理图片文件相关：
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartFile thumbnailFile = null;
        ImageHolder thumbnail = null;
        CommonsMultipartFile productImgFile = null;
        ImageHolder productImage = null;
        //创建文件解析器
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //注意：更改上下架状态不要求上传文件
            if(!statusChange) {
                //判断当前请求中是否存在文件流
                if (multipartResolver.isMultipart(request)) {
                    //强转request，使其强化
                    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                    //操作缩略图
                    thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                    thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                    //操作详情图
                    for (int i = 0; i < 6; i++) {   //最多支持上传6张详情图
                        productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                        if (productImgFile == null) {
                            break;
                        }
                        productImage = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                        productImgList.add(productImage);
                    }
                } else { //没有存在文件流，直接返回错误
                    modelMap.put("success", false);
                    modelMap.put("msg", "上传图片不能为空！");
                    return modelMap;
                }
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.toString());
            return modelMap;
        }

        //处理商品表单普通数据：
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (IOException e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.toString());
            return modelMap;
        }

        //最后的修改操作：
        try {
            if (product != null) {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ProductExecution productExecution = productService.modifyProduct(thumbnail, productImgList, product);
                if (productExecution.getStatus() == ProductStatusEnum.SUCCESS.getStatus()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("msg", productExecution.getStatusInfo());
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("msg", "请输入商品信息");
            }
        } catch (ProductOperationException e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.toString());
            return modelMap;
        }
        return modelMap;
    }

    @RequestMapping("/get_product_list")
    @ResponseBody
    private Map<String, Object> getProductList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取前台传来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取前台传来的每页显示个数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop shop = (Shop)request.getSession().getAttribute("currentShop");
        //非空判断
        if (pageSize >= 0 && pageIndex >= 0 && shop != null && shop.getShopId() != null) {
            Product product = new Product();
            product.setShop(shop);
            //尝试从前台获取商品分类id和商品名称
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            //获取到值，则作为查询条件往product里设置
            if (productCategoryId != -1) {
                ProductCategory productCategory = new ProductCategory();
                productCategory.setProductCategoryId(productCategoryId);
                product.setProductCategory(productCategory);
            }else if (productName != null) {
                product.setProductName(productName);
            }
            //条件查询操作：
            ProductExecution productExecution = productService.getProductList(product, pageIndex, pageSize);
            modelMap.put("productList", productExecution.getProductList());
            modelMap.put("count", productExecution.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("msg", "parameter empty!");
        }
        return modelMap;
    }
}
