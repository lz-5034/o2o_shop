import com.imooc.o2o.dao.ProductCategoryDao;
import com.imooc.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LZ
 * @date 2020/2/28 21:27:32
 * @description
 */
public class TestProductCategory extends Parent {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void test1() {
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryName("测试商品分类1");
        productCategory1.setPriority(99);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(1L);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("测试商品分类2");
        productCategory2.setPriority(101);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1L);

        List<ProductCategory> list = new ArrayList<>();
        list.add(productCategory1);
        list.add(productCategory2);

        int i = productCategoryDao.batchInsertProductCategory(list);
        System.out.println(i);
    }

    @Test
    public void test2() {
        int i = productCategoryDao.deleteProductCategory(1L, 1L);
        int i1 = productCategoryDao.deleteProductCategory(2L, 1L);
        System.out.println(i+i1);
    }

}
