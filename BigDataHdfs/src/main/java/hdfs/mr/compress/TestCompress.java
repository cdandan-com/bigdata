package hdfs.mr.compress;

import com.hadoop.compression.lzo.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/3/13.
 */
public class TestCompress {

    public static void main(String[] args) throws Exception {
        Class[] zipClasses = {
                DeflateCodec.class,
                GzipCodec.class,
                BZip2Codec.class,
                Lz4Codec.class,
                //SnappyCodec.class
                com.hadoop.compression.lzo.LzoCodec.class
        };

        for (Class c : zipClasses) {
            zip(c);
        }
        System.out.println("=================");
        for (Class c : zipClasses) {
            unzip(c);
        }
    }

    /**
     * 压缩测试
     */
    public static void zip(Class codecClass) throws Exception {
        long start = System.currentTimeMillis();
        //实例化对象
        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass, new Configuration());
        //创建文件输出流,得到默认扩展名
        FileOutputStream fos = new FileOutputStream("/home/centos/zip/b" + codec.getDefaultExtension());
        //得到压缩流
        CompressionOutputStream zipOut = codec.createOutputStream(fos);
        IOUtils.copyBytes(new FileInputStream("/home/centos/zip/a.txt"), zipOut, 1024);
        zipOut.close();
        System.out.println(codecClass.getSimpleName() + " : " + (System.currentTimeMillis() - start));
    }

    /**
     * 压缩测试
     */
    public static void unzip(Class codecClass) throws Exception {
        long start = System.currentTimeMillis();
        //实例化对象
        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(codecClass, new Configuration());
        //创建文件输出流,得到默认扩展名
        FileInputStream fis = new FileInputStream("/home/centos/zip/b" + codec.getDefaultExtension());
        //得到压缩流
        CompressionInputStream zipIn = codec.createInputStream(fis);
        IOUtils.copyBytes(zipIn, new FileOutputStream("/home/centos/zip/b" + codec.getDefaultExtension() + ".txt"), 1024);
        zipIn.close();
        System.out.println(codecClass.getSimpleName() + " : " + (System.currentTimeMillis() - start));
    }
}
