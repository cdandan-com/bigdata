package hdfs.mr.mapjoin;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 组合Key排序对比器
 */
public class ComboKey2Comparator extends WritableComparator {
    protected ComboKey2Comparator() {
        super(ComboKey2.class, true);
    }

    public int compare(WritableComparable a, WritableComparable b) {
        ComboKey2 k1 = (ComboKey2) a;
        ComboKey2 k2 = (ComboKey2) b;
        return k1.compareTo(k2);
    }
}