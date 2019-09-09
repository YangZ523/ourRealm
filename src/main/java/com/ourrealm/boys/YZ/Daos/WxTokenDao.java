package com.ourrealm.boys.YZ.Daos;

import com.ourrealm.boys.YZ.Models.WxToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *Mapper注释，使得此接口和mapper.xml文件中的select方法一一对应
 */
@Mapper
public interface WxTokenDao {
    void insert(WxToken wxToken);
    WxToken getWxTokenByAppId(String appId, String type);

    void update(WxToken wxToken);

    List<WxToken> findAll();
}
