package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductStatusEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.utils.ImageUtil;
import com.imooc.o2o.utils.PageCalculator;
import com.imooc.o2o.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LZ
 * @date 2020/2/29 23:41:00
 * @description
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    /**
     * 添加商品功能
     * 1.处理缩略图，获取缩略图相对路径并赋值给product
     * 2.往数据表product写入商品信息，获取productId
     * 3.结合productId批量处理商品详情图
     * 4.往数据表product_img批量插入商品详情图信息
     * @param thumbnail 缩略图
     * @param productImgList 详情图集合
     * @param product 商品的基本信息
     * @return
     * @throws ProductOperationException
     */
    @Override
    @Transactional
    public ProductExecution addProduct(ImageHolder thumbnail, List<ImageHolder> productImgList, Product product) throws ProductOperationException {
        try {
            if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
                //给商品设置默认属性
                product.setCreateTime(new Date());
                product.setLastEditTime(new Date());
                //默认为上架状态
                product.setEnableStatus(1);
                //若商品缩略图不为空则添加
                if (thumbnail != null) {
                    addThumbnail(thumbnail, product);
                }
                //添加商品
                int i = productDao.insertProduct(product);
                if (i <= 0) {
                    throw new ProductOperationException("创建商品失败！");
                }
                //若商品详情图不为空则添加
                if (productImgList != null && productImgList.size() > 0) {
                    addProductImgList(productImgList, product);
                }
                return new ProductExecution(ProductStatusEnum.SUCCESS);
            } else {
                return new ProductExecution(ProductStatusEnum.EMPTY);
            }
        } catch (ProductOperationException e) {
            throw new ProductOperationException("添加商品操作内部出现错误！" + e.toString());
        }
    }

    /**
     * 根据商品id返回完整商品信息
     * @param productId
     * @return
     */
    @Override
    public Product getProductById(long productId) {
        return productDao.selectProductById(productId);
    }

    /**
     * 修改商品功能
     * 1.若缩略图有值，则删除完再添加
     * 2.若详情图有值，则删除完再添加
     * 3.更新product数据库中该商品的信息
     * @param thumbnail 缩略图
     * @param productImgList 详情图集合
     * @param product 商品的基本信息
     * @return
     * @throws ProductOperationException
     */
    @Override
    public ProductExecution modifyProduct(ImageHolder thumbnail, List<ImageHolder> productImgList, Product product) throws ProductOperationException {
        //空值判断
        if (product != null && product.getProductId()!= null && product.getShop() != null && product.getShop().getShopId() != null) {
            try {
                //给商品设置默认属性
                product.setLastEditTime(new Date());
                //处理缩略图
                if (thumbnail != null) {
                    Product originalProduct = productDao.selectProductById(product.getProductId());
                    if (originalProduct.getImgAddr() != null) {
                        //将原有的文件删除
                        ImageUtil.deleteFileOrPath(originalProduct.getImgAddr());
                    }
                    //添加后传来的缩略图
                    addThumbnail(thumbnail,product);
                }
                //处理详情图
                if (productImgList != null && productImgList.size() > 0) {
                    //将原先所有属于该商品的详情图片删除
                    removeProductImgList(product.getProductId());
                    addProductImgList(productImgList, product);
                }
                //更新商品全部信息
                int i = productDao.updateProduct(product);
                if (i <= 0) {
                    throw new ProductOperationException("修改商品内部出错！");
                }
                return new ProductExecution(ProductStatusEnum.SUCCESS);
            } catch (ProductOperationException e) {
                throw new ProductOperationException("modifyProduct error : " + e.toString());
            }
        } else {
            return new ProductExecution(ProductStatusEnum.EMPTY);
        }
    }

    /**
     * 根据查询条件返回商品集合，分页显示
     * @param product
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ProductExecution getProductList(Product product, int pageIndex, int pageSize) {
        //根据传入的页码和每页显示个数，返回数据库查询时需要的行号
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.selectProductList(product, rowIndex, pageSize);
        //在相同查询条件下，获取查询的总数
        int count = productDao.selectProductCount(product);
        ProductExecution productExecution = new ProductExecution();
        productExecution.setCount(count);
        productExecution.setProductList(productList);
        return productExecution;
    }

    //添加商品缩略图
    private void addThumbnail(ImageHolder thumbnail, Product product) {
        try {
            //根据shopId生成缩略图的 相对路径（没有文件路径）
            String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
            //在本地存放缩略图，并返回新生成的缩略图的 相对路径（具体到文件路径）
            String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest, "thumbnail");
            product.setImgAddr(thumbnailAddr);
        } catch (Exception e) {
            throw new ProductOperationException("创建缩略图失败 " + e.toString());
        }
    }

    //添加商品详情图
    private void addProductImgList(List<ImageHolder> productImgList, Product product) {
        try {
            //根据shopId生成详情图相对路径（没有文件路径）
            String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
            //创建ProductImg集合，以便后面进行批量添加
            List<ProductImg> list = new ArrayList<>();
            //循环处理每一个详情图
            for (ImageHolder holder : productImgList) {
                ProductImg productImg = new ProductImg();
                String holderAddr = ImageUtil.generateThumbnail(holder, dest, "normal");
                productImg.setImgAddr(holderAddr);
                productImg.setProductId(product.getProductId());
                productImg.setCreateTime(new Date());
                list.add(productImg);
            }
            int i = productImgDao.batchInsertProductImg(list);
            if (i <= 0) {
                throw new ProductOperationException("创建详情图片失败");
            }
        } catch (ProductOperationException e) {
            throw new ProductOperationException("创建详情图片失败 " + e.toString());
        }
    }

    //删除商品详情图
    private void removeProductImgList(long productId) {
        List<ProductImg> imgList = productImgDao.selectProductImgByProductId(productId);
        //将磁盘中详情图片删除掉
        for (ProductImg productImg : imgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        //将数据库中详情图片地址删除
        productImgDao.deleteProductImgByProductId(productId);
    }
}
