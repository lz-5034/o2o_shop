package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStatusEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.utils.ImageUtil;
import com.imooc.o2o.utils.PageCalculator;
import com.imooc.o2o.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author LZ
 * @date 2020/2/21 18:06:59
 * @description
 */
@Service("shopService")
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(ImageHolder shopImg, Shop shop) {
        //空值判断
        if (shop == null) {
            return new ShopExecution(ShopStatusEnum.NULL_SHOP);
        }
        try {
            //给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺操作
            int i = shopDao.insertShop(shop);
            if (i <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (shopImg.getImage() != null) {
                    //存储图片
                    try {
                        addShopImg(shopImg,shop);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error : " + e.getMessage());
                    }
                    //更新店铺的图片地址
                    int i1 = shopDao.updateShop(shop);
                    if (i1 <= 0) {
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("addShop error : " + e.getMessage());
        }
        return new ShopExecution(ShopStatusEnum.CHECK,shop);
    }

    @Override
    public ShopExecution modifyShop(ImageHolder shopImg, Shop shop) throws ShopOperationException {
        try {
            if (shop == null) {
                return new ShopExecution(ShopStatusEnum.NULL_SHOP);
            } else {
                //如果用户更新商店上传了新图片：
                if (shopImg.getImage() != null && shopImg.getImageName() != null && !shopImg.getImageName().equals("")) {
                    Shop originalShop = shopDao.selectByShopId(shop.getShopId());
                    if (originalShop.getShopImg() != null) {    //原先有图片的情况
                        ImageUtil.deleteFileOrPath(originalShop.getShopImg());  //将旧图片删除
                    }
                    addShopImg(shopImg,shop);    //添加新图片
                }
                //更新店铺信息
                shop.setLastEditTime(new Date());
                int i = shopDao.updateShop(shop);
                if (i <= 0) {
                    return new ShopExecution(ShopStatusEnum.INNER_ERROR);
                } else {
                    shop = shopDao.selectByShopId(shop.getShopId());//重新查询，更新完后的shop返回
                    return new ShopExecution(ShopStatusEnum.SUCCESS, shop);
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("modifyShop error : " + e.getMessage());
        }
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.selectByShopId(shopId);
    }

    @Override
    public ShopExecution getShopList(Shop shop, int pageIndex, int pageSize) {
        //先将用户传来需要查询的第几页数，转换成数据库查询的行索引
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.selectShopList(shop, rowIndex, pageSize);
        Integer count = shopDao.selectShopListCount(shop);
        ShopExecution shopExecution = new ShopExecution();
        if (shopList != null) {
            shopExecution.setShopList(shopList);
            shopExecution.setCount(count);
        } else {
            shopExecution.setStatus(ShopStatusEnum.INNER_ERROR.getStatus());
        }
        return shopExecution;
    }

    //添加缩略图
    private void addShopImg(ImageHolder shopImg,Shop shop) {
        //通过shop id 生成该商品图片对应的相对路径（目标文件路径）
        String shopImgPath = PathUtil.getShopImgPath(shop.getShopId());
        //传入 （原始上传图片输入流，原始上传图片名称，目标文件路径） 返回新生成图片的相对路径
        String s = ImageUtil.generateThumbnail(shopImg, shopImgPath, "thumbnail");
        shop.setShopImg(s);
    }
}
