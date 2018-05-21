package hdfs.mr.skew1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 *解析数据倾斜问题
 */
public class WCSkewApp {
    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        Job job = Job.getInstance(conf);

        //设置job的各种属性
        job.setJobName("WCSkewApp");                        //作业名称
        job.setJarByClass(WCSkewApp.class);                 //搜索类
        job.setInputFormatClass(TextInputFormat.class);     //设置输入格式

        //添加输入路径
        FileInputFormat.addInputPath(job,new Path("d:/mr/skew"));
        //设置输出路径
        FileOutputFormat.setOutputPath(job,new Path("d:/mr/skew/out"));


        //设置合成类
        job.setPartitionerClass(RandomPartitioner.class);   //设置分区类

        job.setMapperClass(WCSkewMapper.class);             //mapper类
        job.setReducerClass(WCSkewReducer.class);           //reducer类

        job.setNumReduceTasks(4);                       //reduce个数

        job.setMapOutputKeyClass(Text.class);           //
        job.setMapOutputValueClass(IntWritable.class);  //

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);     //

        job.waitForCompletion(true);
    }
}
