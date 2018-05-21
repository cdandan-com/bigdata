package hdfs.mr.mapjoin;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * ReduceJoinReducer,reducer端连接实现。
 */
public class ReduceJoinReducer extends Reducer<ComboKey2,NullWritable,Text,NullWritable> {

    protected void reduce(ComboKey2 key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        Iterator<NullWritable> it = values.iterator();
        it.next();
        int type = key.getType();
        int cid = key.getCid() ;
        String cinfo = key.getCustomerInfo() ;
        while(it.hasNext()){
            it.next();
            String oinfo = key.getOrderInfo();
            context.write(new Text(cinfo + "," + oinfo),NullWritable.get());
        }
    }
}