package avrodemo.test;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/3/23.
 */
public class TestAvro {

//    @Test
//    public void write() throws Exception {
//        //创建writer对象
//        SpecificDatumWriter empDatumWriter = new SpecificDatumWriter<Employee>(Employee.class);
//        //写入文件
//        DataFileWriter<Employee> empFileWriter = new DataFileWriter<Employee>(empDatumWriter);
//
//        //创建对象
//        Employee e1 = new Employee();
//        e1.setName("tomas");
//        e1.setAge(12);
//
//        //串行化数据到磁盘
//        empFileWriter.create(e1.getSchema(), new File("d:/avro/data/e1.avro"));
//        empFileWriter.append(e1);
//        empFileWriter.append(e1);
//        empFileWriter.append(e1);
//        empFileWriter.append(e1);
//        //关闭流
//        empFileWriter.close();
//    }
//
//    @Test
//    public void read() throws Exception {
//        //创建writer对象
//        SpecificDatumReader empDatumReader = new SpecificDatumReader<Employee>(Employee.class);
//        //写入文件
//        DataFileReader<Employee> dataReader = new DataFileReader<Employee>(new File("d:/avro/data/e1.avro")  ,empDatumReader);
//        Iterator<Employee> it = dataReader.iterator();
//        while(it.hasNext()){
//            System.out.println(it.next().getName());
//        }
//    }

    /**
     * 直接使用schema文件进行读写，不需要编译
     */
    @Test
    public void writeInSchema() throws  Exception {
        //指定定义的avsc文件。
        Schema schema = new Schema.Parser().parse(new File("d:/avro/emp.avsc"));

        //创建GenericRecord,相当于Employee
        GenericRecord e1 = new GenericData.Record(schema);
        //设置javabean属性
        e1.put("name", "ramu");
//        e1.put("id", 001);
//        e1.put("salary", 30000);
        e1.put("age", 25);
//        e1.put("address", "chennai");

        //
        DatumWriter w1 = new SpecificDatumWriter (schema);
        DataFileWriter w2 = new DataFileWriter(w1);
        w2.create(schema,new File("d:/avro/data/e2.avro")) ;
        w2.append(e1);
        w2.append(e1);
        w2.close();
    }

    /**
     * 反串行avro数据
     */
    @Test
    public void readInSchema() throws  Exception {
        //指定定义的avsc文件。
        Schema schema = new Schema.Parser().parse(new File("d:/avro/emp.avsc"));

        GenericRecord e1 = new GenericData.Record(schema);
        DatumReader r1 = new SpecificDatumReader (schema);
        DataFileReader r2 = new DataFileReader(new File("d:/avro/data/e2.avro"),r1);
        while(r2.hasNext()){
            GenericRecord rec = (GenericRecord)r2.next();
            System.out.println(rec.get("name"));
        }
        r2.close();
    }

}
