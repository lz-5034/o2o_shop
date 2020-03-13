import com.imooc.o2o.dao.ShopCategoryDao;
import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author LZ
 * @date 2020/2/23 22:23:08
 * @description
 */
public class TestShopDao extends Parent {

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void test1() {
        Shop shop = shopDao.selectByShopId(6L);
        System.out.println(shop);
        System.out.println(shop.getArea());
        System.out.println(shop.getArea().getAreaId());
        System.out.println(shop.getArea().getAreaName());
        System.out.println(shop.getShopCategory().getShopCategoryId());
        System.out.println(shop.getShopCategory().getShopCategoryName());
    }

    @Test
    public void test2() {
        Shop shop = new Shop();
        shop.setShopName("飘飘");
        List<Shop> shopList = shopDao.selectShopList(shop, 0, 2);
        for (Shop shop1 : shopList) {
            System.out.println(shop1.getShopName());
        }

        System.out.println("总数"+shopDao.selectShopListCount(shop));
    }

    @Test
    public void test3() {
        List<ShopCategory> list1 = shopCategoryDao.selectShopCategory(null);
        List<ShopCategory> list2 = shopCategoryDao.selectShopCategory(new ShopCategory());

        System.out.println(list1.size());
        System.out.println(list2.size());
    }

    @Test
    public void test4() {
        Shop shop = new Shop();
        ShopCategory child = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(3L);
        child.setParent(parent);
        shop.setShopCategory(child);
        List<Shop> shopList = shopDao.selectShopList(shop, 0, 10);
        Integer count = shopDao.selectShopListCount(shop);
        for (Shop shop1 : shopList) {
            System.out.println(shop1.getShopName());
        }
        System.out.println(shopList.size());
        System.out.println(count);
    }
}
