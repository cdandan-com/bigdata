package hdfs.mr.mapjoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *
 */
public class ReduceJoinApp {
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");

        Job job = Job.getInstance(conf);

        //设置job的各种属性
        job.setJobName("ReduceJoinApp");                        //作业名称
        job.setJarByClass(ReduceJoinApp.class);                 //搜索类

        //添加输入路径
        FileInputFormat.addInputPath(job,new Path("D:\\mr\\reducejoin"));
        //设置输出路径
        FileOutputFormat.setOutputPath(job,new Path("D:\\mr\\reducejoin\\out"));

        job.setMapperClass(ReduceJoinMapper.class);             //mapper类
        job.setReducerClass(ReduceJoinReducer.class);           //reducer类

        //设置Map输出类型
        job.setMapOutputKeyClass(ComboKey2.class);            //
        job.setMapOutputValueClass(NullWritable.class);      //

        //设置ReduceOutput类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);         //

        //设置分区类
        job.setPartitionerClass(CIDPartitioner.class);
        //设置分组对比器
        job.setGroupingComparatorClass(CIDGroupComparator.class);
        //设置排序对比器
        job.setSortComparatorClass(ComboKey2Comparator.class);
        job.setNumReduceTasks(2);                           //reduce个数
        job.waitForCompletion(true);
    }
}