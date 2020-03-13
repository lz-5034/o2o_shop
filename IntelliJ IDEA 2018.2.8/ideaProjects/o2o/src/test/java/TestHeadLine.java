import com.imooc.o2o.dao.HeadLineDao;
import com.imooc.o2o.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author LZ
 * @date 2020/3/4 11:31:44
 * @description
 */
public class TestHeadLine extends Parent {
    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void test1() {
        HeadLine headLine = new HeadLine();
        headLine.setEnableStatus(1);
        List<HeadLine> list = headLineDao.selectHeadLineList(headLine);
        System.out.println(list.size());
    }
}
