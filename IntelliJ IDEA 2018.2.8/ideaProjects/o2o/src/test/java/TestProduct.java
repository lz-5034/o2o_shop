import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStatusEnum;
import com.imooc.o2o.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LZ
 * @date 2020/2/29 22:35:31
 * @description
 */
public class TestProduct extends Parent {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;
    @Autowired
    private ProductService productService;
    @Test
    public void test1() {
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(3L);

        Product product = new Product();
        product.setProductName("可乐");
        product.setProductDesc("好喝的可乐哦");
        product.setImgAddr("地址...");
        product.setOriginalPrice("100");
        product.setDiscountPrice("69");
        product.setPriority(100);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setEnableStatus(1);
        product.setProductCategory(productCategory);
        product.setShop(shop);

        int i = productDao.insertProduct(product);
        System.out.println(i);
    }

    @Test
    public void test2() {
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1地址");
        productImg1.setImgDesc("啦啦啦");
        productImg1.setPriority(100);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(1L);

        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2地址");
        productImg2.setImgDesc("啦啦啦");
        productImg2.setPriority(110);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1L);


        ProductImg productImg3 = new ProductImg();
        productImg3.setImgAddr("图片3地址");
        productImg3.setImgDesc("啦啦啦");
        productImg3.setPriority(105);
        productImg3.setCreateTime(new Date());
        productImg3.setProductId(1L);

        List<ProductImg> list = new ArrayList<>();
        list.add(productImg1);
        list.add(productImg2);
        list.add(productImg3);

        int i = productImgDao.batchInsertProductImg(list);
        System.out.println(i);
    }

    @Test
    public void test3() throws FileNotFoundException {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(6L);
        Shop shop = new Shop();
        shop.setShopId(5L);

        Product product = new Product();
        product.setProductCategory(productCategory);
        product.setShop(shop);
        product.setProductName("安慕希");
        product.setProductDesc("好喝");
        product.setOriginalPrice("100");
        product.setDiscountPrice("56");
        product.setPriority(55);

        File file1 = new File("d:/1.jpg");
        FileInputStream inputStream1 = new FileInputStream(file1);
        ImageHolder imageHolder1 = new ImageHolder(file1.getName(), inputStream1);

        File file2 = new File("d:/1.jpg");
        FileInputStream inputStream2 = new FileInputStream(file2);
        File file3 = new File("d:/1.jpg");
        FileInputStream inputStream3 = new FileInputStream(file3);
        ImageHolder imageHolder2 = new ImageHolder(file2.getName(), inputStream2);
        ImageHolder imageHolder3 = new ImageHolder(file3.getName(), inputStream3);
        List<ImageHolder> list = new ArrayList<>();
        list.add(imageHolder2);
        list.add(imageHolder3);

        ProductExecution productExecution = productService.addProduct(imageHolder1, list, product);
        ProductStatusEnum statusEnum = ProductStatusEnum.SUCCESS;
        System.out.println(productExecution.getStatusInfo().equals(statusEnum.getStatusInfo()));
    }

    @Test
    public void test4() {
        Product product = productDao.selectProductById(5L);
        System.out.println(product.getProductName() + ".." + product.getImgAddr());
    }

    @Test
    public void test5() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(6L);
        Shop shop = new Shop();
        shop.setShopId(5L);

        Product product = new Product();
        product.setProductId(5L);
        product.setProductCategory(productCategory);
        product.setShop(shop);
        product.setProductName("测试食品");
        product.setProductDesc("不知道味道");
        product.setOriginalPrice("200");
        product.setDiscountPrice("98");
        product.setPriority(65);

        int i = productDao.updateProduct(product);
        System.out.println(i);
    }

    @Test
    public void test6() {
        int i = productImgDao.deleteProductImgByProductId(5L);
        System.out.println(i);
    }

    @Test
    public void test7() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(5L);

        Product product = new Product();
        product.setProductId(5L);
        product.setShop(shop);
        product.setProductName("汉堡包1");
        product.setProductDesc("很好吃啊1");
        //imgAddr = 2568
        File file = new File("d:/1.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        ImageHolder imageHolder = new ImageHolder(file.getName(), inputStream);

        //imgAddr = 2834  6422
        File file1 = new File("d:/1.jpg");
        FileInputStream inputStream1 = new FileInputStream(file1);
        File file2 = new File("d:/1.jpg");
        FileInputStream inputStream2 = new FileInputStream(file2);
        List<ImageHolder> list = new ArrayList<>();
        ImageHolder imageHolder1 = new ImageHolder(file1.getName(), inputStream1);
        ImageHolder imageHolder2 = new ImageHolder(file2.getName(), inputStream2);
        list.add(imageHolder1);
        list.add(imageHolder2);

        ProductExecution productExecution = productService.modifyProduct(imageHolder, list, product);
        ProductStatusEnum statusEnum = ProductStatusEnum.SUCCESS;
        System.out.println(statusEnum.getStatus() == productExecution.getStatus());
    }

    @Test
    public void test8() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(3L);

        Product product = new Product();
        product.setProductCategory(productCategory);
        product.setProductName("安");

        List<Product> products = productDao.selectProductList(product, 0, 5);
        for (Product product1 : products) {
            System.out.println(product1.getProductDesc());
        }
        int i = productDao.selectProductCount(product);
        System.out.println(i);
    }
}
