package com.ourrealm.boys.YZ.Services;

import com.ourrealm.boys.YZ.Daos.MiniUserDao;
import com.ourrealm.boys.YZ.Models.MiniUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class MiniUserService {

    @Autowired
    private MiniUserDao miniUserDao;

    public void addUserInfo(MiniUser user) {
        miniUserDao.insert(user);
    }

    public MiniUser findByOpenid(String openid) {
        try {
            Map<String,Object> map=new HashMap<>();
            map.put("openid",openid);
            return 	miniUserDao.findByOpenid(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void save(MiniUser miniUser) {
        miniUserDao.insert(miniUser);
    }

    public void update(MiniUser miniUser) {
        miniUserDao.update(miniUser);
    }
}
