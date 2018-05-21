import com.yuan.domain.Order;
import com.yuan.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 */
public class TestUser {

    /**
     * insert
     */
    @Test
    public void insert() throws Exception {
        //指定配置文件的路径(类路径)
        String resource = "mybatis-config.xml";
        //加载文件
        InputStream inputStream = Resources.getResourceAsStream(resource);

        //创建会话工厂Builder,相当于连接池
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);

        //通过sf开启会话，相当于打开连接。
        SqlSession s = sf.openSession();
        User u = new User();

        u.setName("jerry");
        u.setAge(2);
        s.insert("users.insert", u);
        s.commit();
        s.close();
    }

    /**
     * update
     */
    @Test
    public void update() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();
        User u = new User();
        u.setId(1);
        u.setName("tomas");
        u.setAge(32);
        s.update("users.update", u);
        s.commit();
        s.close();
    }

    /**
     * selectOne
     */
    @Test
    public void selectOne() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();
        User user = s.selectOne("users.selectOne",1);
        System.out.println(user.getName());
        for(Order o : user.getOrders()){
            System.out.println(o.getId() + " :" + o.getOrderNo());
        }
        s.commit();
        s.close();
    }

    /**
     * selectOne
     */
    @Test
    public void selectAll() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();
        List<User> users = s.selectList("users.selectAll");
        for(User uu : users){
            System.out.println(uu.getName() + "," + uu.getAge());
        }
        s.commit();
        s.close();
    }
}
