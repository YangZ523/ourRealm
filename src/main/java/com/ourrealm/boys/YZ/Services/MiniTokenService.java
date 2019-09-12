package com.ourrealm.boys.YZ.Services;

import com.ourrealm.boys.YZ.BaseConfig.CharUtil;
import com.ourrealm.boys.YZ.Daos.MiniTokenDao;
import com.ourrealm.boys.YZ.Models.MiniToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class MiniTokenService {

    @Autowired
    private MiniTokenDao miniTokenDao;

    /**
     * 30天过期
     */
    private final static Long EXPIRE = 3600 * 24 * 30L;

    private void save(MiniToken miniToken) {
        miniTokenDao.insert(miniToken);
    }

    private void update(MiniToken miniToken) {
        miniTokenDao.update(miniToken);
    }

    /**
     * 自己生成token，过期时间30天
     * @param id
     * @return
     */
    public Map<String,Object> createToken(int id) {
        //生成一个新的token
        String token=CharUtil.getRandomString(32);
        //当前时间
        Date now=new Date();
        //过期时间30天
        Date expireTume=new Date(now.getTime()+EXPIRE*1000);
        //判断是否生成过，记录过此用户的登录状态
        MiniToken miniToken=queryByUserId(id);
        if (miniToken==null){
            //新用户
            miniToken =new MiniToken();
            miniToken.setUserToken(token);
            miniToken.setExpireTime(expireTume);
            miniToken.setId(id);
            miniToken.setUpdateTime(now);
            //保存此用户的登录标识
            save(miniToken);
        }else {
            //老用户，更新登录标识
            miniToken.setUserToken(token);
            miniToken.setExpireTime(expireTume);
            miniToken.setUpdateTime(now);
            update(miniToken);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("token",token);
        map.put("expire",EXPIRE);
        return map;
    }

    /**
     * 根据用户id查询出token对象(用户id和token的主键id相同)
     * @param id
     * @return
     */
    private MiniToken queryByUserId(int id) {
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("id",id);
            return 	miniTokenDao.getByUserId(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
