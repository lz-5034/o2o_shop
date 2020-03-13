import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author LZ
 * @date 2020/2/20 22:01:29
 * @description
 */
public class Test extends Parent {

    @Autowired
    private ShopDao shopDao;

    @org.junit.Test
    public void test1() {
        User user = new User();
        user.setUserId(1L);
        Area area = new Area();
        area.setAreaId(1);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(1L);
        Shop shop = new Shop();
        shop.setUser(user);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("香飘飘奶茶店");
        shop.setShopDesc("这是新开的店");
        shop.setPriority(100);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(0);
        shopDao.insertShop(shop);
    }

    @org.junit.Test
    public void test2() {
        User user = new User();
        user.setUserId(1L);
        Area area = new Area();
        area.setAreaId(1);

        Shop shop = new Shop();
        shop.setShopId(1L);

        shop.setUser(user);
        shop.setArea(area);
        shop.setShopName("改了香飘飘奶茶店");
        shop.setShopDesc("这不是新开的店");
        shop.setPriority(101);
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(0);
        shopDao.updateShop(shop);
    }
}
