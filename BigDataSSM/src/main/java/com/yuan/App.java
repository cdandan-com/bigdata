package com.yuan;


import com.yuan.dao.UserDao;
import com.yuan.domain.User;

import java.util.List;

/**
 *
 */
public class App {
    public static void main(String[] args) {
        UserDao dao = new UserDao();
//        User u = dao.selctOne(1);
//        System.out.println(u.getName());
        List<User> list = dao.selctAll();
        for(User u : list){
            System.out.println(u.getName());
        }
        
    }
}
