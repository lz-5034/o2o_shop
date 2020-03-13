import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author LZ
 * @date 2020/3/11 23:23:09
 * @description
 */
public class TestArea extends Parent {
    @Autowired
    private AreaService areaService;

    @Test
    public void test1() {
        List<Area> list = areaService.getAllArea();
        for (Area area : list) {
            System.out.println(area.getAreaName());
        }
    }
}
