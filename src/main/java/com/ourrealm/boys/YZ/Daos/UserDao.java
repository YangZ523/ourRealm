package com.ourrealm.boys.YZ.Daos;


import com.ourrealm.boys.YZ.Models.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    User getUserInfoById(int id);

    void insert(User user);

    int update(User user);
}
