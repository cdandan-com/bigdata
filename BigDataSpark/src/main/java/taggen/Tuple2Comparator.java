package taggen;

import scala.Tuple2;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/5/12.
 */
public class Tuple2Comparator  implements Comparator<Tuple2<String,Integer>>{
    public int compare(Tuple2<String, Integer> o1, Tuple2<String, Integer> o2) {
        return o2._2() - o1._2() ;
    }
}
