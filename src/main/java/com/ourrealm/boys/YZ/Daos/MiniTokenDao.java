package com.ourrealm.boys.YZ.Daos;

import com.ourrealm.boys.YZ.Models.MiniToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MiniTokenDao {

    void insert(MiniToken miniToken);

    int update(MiniToken miniToken);

    MiniToken getByUserId(Map<String, Object> map);
}
