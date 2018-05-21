import com.yuan.domain.User;
import com.yuan.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;


/**
 * 测试数据源
 */
public class TestDataSource {
    @Test
    public void testConn() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        DataSource ds = (DataSource)ac.getBean("dataSource");
        System.out.println(ds.getConnection());
    }

    @Test
    public void testUserService() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        UserService us = (UserService)ac.getBean("userService");
        User u = new User();
        u.setName("jerry");
        u.setAge(12);
        us.insert(u);
    }
}
