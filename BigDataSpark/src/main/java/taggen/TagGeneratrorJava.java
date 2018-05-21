package taggen;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import scala.collection.immutable.Nil;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Administrator on 2017/5/12.
 */
public class TagGeneratrorJava {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setAppName("xxx");
        conf.setMaster("local[4]");

        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> rdd1 = sc.textFile("file:///D:/scala/taggen/temptags.txt");
        //切割
        JavaRDD<String[]> rdd2 = rdd1.map(new Function<String, String[]>() {
            public String[] call(String v1) throws Exception {
                return v1.split("\t");
            }
        });
        //过滤
        JavaRDD<String[]> rdd3 = rdd2.filter(new Function<String[], Boolean>() {
            public Boolean call(String[] v1) throws Exception {
                return v1.length == 2;
            }
        });
        //变换成数组,12345->味道好,上菜快,环境好
        JavaPairRDD<String,String> rdd4 = rdd3.mapToPair(new PairFunction<String[], String, String>() {
            public Tuple2<String, String> call(String[] v) throws Exception {
                return new Tuple2<String, String>(v[0], ReviewTags.extractTags(v[1]));
            }
        });
        //过滤店家的有效评论
        JavaPairRDD<String,String> rdd5 = rdd4.filter(new Function<Tuple2<String, String>, Boolean>() {
            public Boolean call(Tuple2<String, String> v1) throws Exception {
                return v1._2().length() > 0;
            }
        });

        //将评论切割成数组
        JavaPairRDD<String,String[]> rdd6 = rdd5.mapToPair(new PairFunction<Tuple2<String,String>, String, String[]>() {
            public Tuple2<String, String[]> call(Tuple2<String, String> t) throws Exception {
                return new Tuple2<String, String[]>(t._1(),t._2().split(","));
            }
        });
        //12345->味道好,12345->上菜快,12345->环境好
        JavaPairRDD<String,String> rdd7 = rdd6.flatMapValues(new Function<String[], Iterable<String>>() {
            public Iterable<String> call(String[] v1) throws Exception {
                List<String> list = new ArrayList<String>();
                for(String s : v1){
                    list.add(s);
                }
                return list;
            }
        });
        //标1成对
        JavaPairRDD<Tuple2<String, String>, Integer> rdd8 = rdd7.mapToPair(new PairFunction<Tuple2<String,String>, Tuple2<String, String>, Integer>() {
            public Tuple2<Tuple2<String, String>, Integer> call(Tuple2<String, String> t) throws Exception {
                return new Tuple2<Tuple2<String, String>, Integer>(t,1);
            }
        });
        //聚合(12345->味道好)->30,(12345->上菜快)->80
        JavaPairRDD<Tuple2<String, String>, Integer> rdd9 = rdd8.reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        //(12345,(味道好->30),12345,(上菜快->80)
        JavaPairRDD<String,Tuple2<String, Integer>> rdd10 = rdd9.mapToPair(new PairFunction<Tuple2<Tuple2<String,String>,Integer>, String, Tuple2<String,Integer>>() {
            public Tuple2<String, Tuple2<String, Integer>> call(Tuple2<Tuple2<String, String>, Integer> t) throws Exception {
                return new Tuple2<String, Tuple2<String, Integer>>(t._1()._1(),new Tuple2<String, Integer>(t._1()._2(),t._2()));
            }
        });

        //变换value成集合，以备聚合
        JavaPairRDD<String,List<Tuple2<String,Integer>>> rdd11 = rdd10.mapToPair(new PairFunction<Tuple2<String,Tuple2<String,Integer>>, String, List<Tuple2<String, Integer>>>() {
            public Tuple2<String, List<Tuple2<String, Integer>>> call(Tuple2<String, Tuple2<String, Integer>> t) throws Exception {
                List<Tuple2<String, Integer>> list = new ArrayList<Tuple2<String, Integer>>();
                list.add(t._2());
                return new Tuple2<String, List<Tuple2<String, Integer>>>(t._1(), list);
            }
        });

        //聚合
        JavaPairRDD<String, List<Tuple2<String, Integer>>> rdd12 = rdd11.reduceByKey(new Function2<List<Tuple2<String, Integer>>, List<Tuple2<String, Integer>>, List<Tuple2<String, Integer>>>() {
            public List<Tuple2<String, Integer>> call(List<Tuple2<String, Integer>> v1, List<Tuple2<String, Integer>> v2) throws Exception {
                v1.addAll(v2);
                return v1 ;
            }
        });

        //聚合12345->[
        //              (->),
        //              (->)
        //              ]

        JavaPairRDD<String,String> rdd13 = rdd12.mapToPair(new PairFunction<Tuple2<String,List<Tuple2<String,Integer>>>, String, String>() {
            public Tuple2<String, String> call(Tuple2<String, List<Tuple2<String, Integer>>> t) throws Exception {
                TreeSet<Tuple2<String, Integer>> ts = new TreeSet<Tuple2<String, Integer>>(new Tuple2Comparator());
                ts.addAll(t._2());
                Iterator<Tuple2<String, Integer>> it = ts.iterator() ;
                int index = 0 ;
                String str = "" ;
                while(it.hasNext()){
                    if(index > 9){
                        break ;
                    }
                    Tuple2<String,Integer> t0 = it.next();
                    str = str + t0._1() + ":" + t0._2() + "," ;
                    index ++ ;
                }
                str = str.substring(0,str.length() - 1) ;
                return new Tuple2<String, String>(t._1(),str) ;
            }
        });

        List<Tuple2<String,String>> data = rdd13.collect();
        for(Tuple2<String, String> tt : data){
            System.out.println(tt._1() + "==>" + tt._2());
        }
    }
}
