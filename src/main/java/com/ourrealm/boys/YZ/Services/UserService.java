package com.ourrealm.boys.YZ.Services;

import com.ourrealm.boys.YZ.Daos.UserDao;
import com.ourrealm.boys.YZ.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUserInfoById(int id) {
        return userDao.getUserInfoById(id);
    }

    public void addUserInfo(User user) {
        userDao.insert(user);
    }

    public void update(User user) {
        int update=userDao.update(user);
        System.out.println("操作结果是"+update);
        //此处声明，UserService类被添加Transactional注解，正类中的所有方法被事务管理，一下是个运行时异常，实验表明，上一句修改执行成功后，出现异常会
        //会滚，进行数据的恢复
        int a=10/0;

        /**
         * 控制台输出：
         * 操作结果是1
         * java.lang.ArithmeticException: / by zero
         * 	at com.yzeng.bootdemo.service.UserService.update(UserService.java:34)
         * 	at com.yzeng.bootdemo.service.UserService$$FastClassBySpringCGLIB$$852280b6.invoke(<generated>)
         */

    }

}
