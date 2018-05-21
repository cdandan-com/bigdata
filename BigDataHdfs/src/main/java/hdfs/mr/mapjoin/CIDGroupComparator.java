package hdfs.mr.mapjoin;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * CID分组对比器
 */
public class CIDGroupComparator extends WritableComparator{

    protected CIDGroupComparator() {
        super(ComboKey2.class, true);
    }

    public int compare(WritableComparable a, WritableComparable b) {
        ComboKey2 k1 = (ComboKey2) a;
        ComboKey2 k2 = (ComboKey2) b;
        return k1.getCid() - k2.getCid();
    }
}