package hdfs.mr.mapjoin;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class CIDPartitioner extends Partitioner<ComboKey2,NullWritable> {

    public int getPartition(ComboKey2 key, NullWritable nullWritable, int numPartitions) {
        return key.getCid() % numPartitions;
    }
}