package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.entity.User;
import com.imooc.o2o.enums.ShopStatusEnum;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
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
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LZ
 * @date 2020/2/22 10:34:25
 * @description
 */
@Controller("shopManagementController")
@RequestMapping("/shop_admin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;


    /**
     * 添加店铺时，初始化相关参数
     * @return
     */
    @RequestMapping("/shop_init")
    @ResponseBody
    private Map<String, Object> initShop() {
        Map<String, Object> modelMap = null;
        List<Area> areaList = null;
        List<ShopCategory> shopCategoryList = null;
        try {
            modelMap = new HashMap<>();
            areaList = areaService.getAllArea();
            shopCategoryList = shopCategoryService.getShopCategory(new ShopCategory());
            modelMap.put("areaList",areaList);
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("success",true);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("msg", e.getMessage());
        }
        return modelMap;
    }

    /**
     * 注册店铺功能
     * @param request
     * @return
     */
    @RequestMapping("/shop_register")
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //先处理验证码
        if (!VerifyCodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("msg", "验证码错误！");
            return modelMap;
        }
        //1.接收并转换相应参数，包括店铺信息和图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);   //将页面读取到的参数转换为shop实体类
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        //定义文件上传解析器，去解析request里面的文件信息
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //判断是否有上传的文件流
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            //通过指定参数获取文件流，并进行转换成spring能够处理的CommonsMultipartFile类型
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("msg", "上传图片不能为空");
            return modelMap;
        }
        //2.注册店铺
        if (shopImg != null && shop != null) {
            User user = (User)request.getSession().getAttribute("user");
            shop.setUser(user);
            ShopExecution shopExecution = null;
            ImageHolder shopImageHolder = null;
            try {
                //将图片输入流和图片名封装到ImageHolder中，供添加店铺时使用
                shopImageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                shopExecution = shopService.addShop(shopImageHolder, shop);
                if (shopExecution.getStatus() == ShopStatusEnum.CHECK.getStatus()) {
                    modelMap.put("success", true);
                    //将该登录用户能够操作的店铺放到session域中
                    List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
                    if (shopList == null) {
                        shopList = new ArrayList<>();
                    }
                    shopList.add(shopExecution.getShop());
                    request.getSession().setAttribute("shopList", shopList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("msg", shopExecution.getStatusInfo());
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("msg", e.getMessage());
                return modelMap;
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("msg", "请输入店铺信息");
            return modelMap;
        }
    }

    /**
     * 修改店铺前，返回被修改店铺的相关信息
     * @param request
     * @return
     */
    @RequestMapping("/get_shop_by_id")
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
            if (shopId > 0) {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAllArea();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("msg", "empty shopId");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.getMessage());
        }
        return modelMap;
    }

    /**
     * 修改店铺信息功能
     * @param request
     * @return
     */
    @RequestMapping("/shop_modify")
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //先处理验证码
        if (!VerifyCodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("msg", "验证码错误！");
            return modelMap;
        }
        //1.接收并转换相应参数，包括店铺信息和图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);   //将页面读取到的参数转换为shop实体类
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        //定义文件上传解析器，去解析request里面的文件信息
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //判断是否有上传的文件流
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            //通过指定参数获取文件流，并进行转换成spring能够处理的CommonsMultipartFile类型
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        //2.修改店铺信息
        if (shop != null && shop.getShopId() != null) {
            ShopExecution shopExecution = null;
            ImageHolder shopImageHolder = null;
            try {
                if (shopImg == null) {
                    shopExecution = shopService.modifyShop(null, shop);
                } else {
                    shopImageHolder = new ImageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                    shopExecution = shopService.modifyShop(shopImageHolder, shop);
                }
                if (shopExecution.getStatus() == ShopStatusEnum.SUCCESS.getStatus()) {
                    //将在session域中的shopList进行更新
                    List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shopList");
                    if (shopList == null) {
                        shopList = new ArrayList<>();
                    } else {
                        for (int i = 0; i < shopList.size(); i++) {
                            if (shopList.get(i).getShopId().equals(shop.getShopId())) {
                                shopList.remove(shopList.get(i));
                                break;
                            }
                        }
                    }
                    shopList.add(shopExecution.getShop());
                    request.getSession().setAttribute("shopList", shopList);
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("msg", shopExecution.getStatusInfo());
                }
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("msg", e.getMessage());
                return modelMap;
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("msg", "请输入店铺id");
            return modelMap;
        }
    }

    /**
     * 通过用户获取该用户所有店铺信息
     * @param request
     * @return
     */
    @RequestMapping("/get_shop_list")
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop shop = null;
        User user = null;
        try {
            user = (User)request.getSession().getAttribute("user");//从session域中获取登录用户
            shop = new Shop();
            shop.setUser(user);
            ShopExecution shopExecution = shopService.getShopList(shop, 1, 100);
            modelMap.put("user", user);
            modelMap.put("shopList", shopExecution.getShopList());
            request.getSession().setAttribute("shopList", shopExecution.getShopList());
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping("/get_shop_management")
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId < 1) {   //用户从其他途径访问管理店铺页面，此时没有店铺id
            Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
            if (currentShop == null) {  //之前没有访问过任何店铺，重定向到店铺列表页面
                modelMap.put("redirect", true);
                modelMap.put("url", "/o2o/shop_admin/shop_list");
            } else {    //用户之前访问过某店铺，将店铺id返回
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        } else {    //用户正常访问管理店铺页面
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            //设置当前店铺
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }

    /*private static void inputStreamToFile(InputStream ins, File file) {
        FileOutputStream outs = null;
        try {
            outs = new FileOutputStream(file);
            int byteRead = 0;
            byte[] buffer = new byte[1024];
            while ((byteRead = ins.read(buffer)) != -1) {
                outs.write(buffer, 0, byteRead);
            }
        } catch (Exception e) {
            throw new RuntimeException("调用inputStreamToFile发生异常 ： " + e.getMessage());
        }finally {
            try {
                outs.close();
                ins.close();
            } catch (Exception e) {
                throw new RuntimeException("inputStreamToFile关闭IO发生异常 ： " + e.getMessage());
            }
        }
    }*/
}
