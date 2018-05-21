package hdfs.mr.secondarysort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 自定义组合key
 */
public class ComboKey implements WritableComparable<ComboKey> {
    private int year ;
    private int temp ;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    /**
     * 对key进行比较实现
     */
    public int compareTo(ComboKey o) {
        int y0 = o.getYear();
        int t0 = o.getTemp() ;
        //年份相同(升序)
        if(year == y0){
            //气温降序
            return -(temp - t0) ;
        }
        else{
            return year - y0 ;
        }
    }

    /**
     * 串行化过程
     */
    public void write(DataOutput out) throws IOException {
        //年份
        out.writeInt(year);
        //气温
        out.writeInt(temp);
    }

    public void readFields(DataInput in) throws IOException {
        year = in.readInt();
        temp = in.readInt();
    }
}
