import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.entity.User;
import com.imooc.o2o.enums.ShopStatusEnum;
import com.imooc.o2o.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * @author LZ
 * @date 2020/2/21 21:55:45
 * @description
 */
public class TestShopService extends Parent {
    @Autowired
    private ShopService shopService;

    @Test
    public void test1() throws FileNotFoundException {
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
        shop.setShopName("service2测试奶茶店");
        shop.setShopDesc("service12试这是新开的店");
        shop.setPriority(10010);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(ShopStatusEnum.CHECK.getStatus());
        File file = new File("d:/gg.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        ImageHolder imageHolder = new ImageHolder(file.getName(), fileInputStream);
        ShopExecution shopExecution = shopService.addShop(imageHolder, shop);
        System.out.println(shopExecution.getStatus());
        System.out.println(shopExecution.getShop().getShopName());
    }

    @Test
    public void test2() {
        Area area = new Area();
        area.setAreaId(1);
        Shop shop = new Shop();
        shop.setArea(area);
        ShopExecution shopList = shopService.getShopList(shop, 1, 2);
        System.out.println("第1页查询个数："+shopList.getShopList().size());
        System.out.println("查询总数："+shopList.getCount());
    }
}
