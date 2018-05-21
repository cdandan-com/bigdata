package com.yuan.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * 工具类
 */
public class Util {
    //
    private static SqlSessionFactory sf ;

    static{
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sf = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启会话
     */
    public static SqlSession openSession(){
        return sf.openSession() ;
    }

    /**
     * 关闭会话
     */
    public static void closeSession(SqlSession s){
        if(s != null){
            s.close();
        }
    }


    /**
     * 关闭会话
     */
    public static void rollbackTx(SqlSession s) {
        if (s != null) {
            s.rollback();
        }
    }


}
