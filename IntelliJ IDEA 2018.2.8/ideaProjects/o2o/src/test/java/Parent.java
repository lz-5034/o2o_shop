import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LZ
 * @date 2020/2/20 21:58:32
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml","classpath:applicationContext_web.xml","classpath:applicationContext_redis.xml"})
public class Parent {
    @Test
    public void test100() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = simpleDateFormat.parse("2016-3-7");
        String format = simpleDateFormat.format(parse);
        System.out.println(parse);
        System.out.println(format);
    }
}
