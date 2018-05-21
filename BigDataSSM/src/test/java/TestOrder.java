import com.yuan.domain.Item;
import com.yuan.domain.Order;
import com.yuan.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 */
public class TestOrder {

    /**
     * insert
     */
    @Test
    public void insert() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();

        User u;
        u = new User();
        u.setId(2);

        Order o = new Order();
        o.setOrderNo("No005");
        o.setUser(u);

        s.insert("orders.insert",o);
        s.commit();
        s.close();
    }

    @Test
    public void selectOne() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();
        Order order = s.selectOne("orders.selectOne",1);
        System.out.println(order.getOrderNo() + order.getUser().getName());
        for(Item i : order.getItems()){
            System.out.println(i.getId() + ":" + i.getItemName());
        }
        s.commit();
        s.close();
    }

    @Test
    public void selectAll() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();
        List<Order> list = s.selectList("orders.selectAll");
        for(Order o : list){
            System.out.println(o.getOrderNo() + " : " + o.getUser().getName());
        }
        s.commit();
        s.close();
    }
}
