package com.ourrealm.boys.YZ.Daos;

import com.ourrealm.boys.YZ.Models.MiniUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MiniUserDao {

    void insert(MiniUser user);

    int update(MiniUser user);

    MiniUser findByOpenid(Map<String, Object> map);
}
