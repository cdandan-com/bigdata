import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/21.
 */
public class TestData {
    @Test
    public void test1(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy/MM/dd HH:mm:ss");
        System.out.println(sdf.format(date));
    }
}
