package hdfs;

/**
 * Created by Administrator on 2017/3/10.
 */
public class Singleton {
    static{
        c1 = 0 ;
        c2 = 0 ;
    }
    private static Singleton instance = new Singleton();
    public static int c1 ;
    public static int c2 = 0 ;

    private Singleton(){
        c1++ ;
        c2++;
    }


    public static Singleton getInstance(){
        return instance ;
    }
}
