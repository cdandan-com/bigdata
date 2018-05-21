package hdfs.mr.secondarysort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 自定义分区类
 */
public class YearPartitioner extends Partitioner<ComboKey,NullWritable> {

    public int getPartition(ComboKey key, NullWritable nullWritable, int numPartitions) {
        int year = key.getYear();
        return year % numPartitions;
    }
}
