package com.ourrealm.boys.YZ.Services;

import com.ourrealm.boys.YZ.BaseConfig.Wxoauth2token;
import com.ourrealm.boys.YZ.Daos.WxTokenDao;
import com.ourrealm.boys.YZ.Models.WxToken;
import com.ourrealm.boys.YZ.utils.IdGenerate;
import com.ourrealm.boys.YZ.utils.QiYeWeixinHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class WxTokenService {

    @Autowired
    private WxTokenDao wxTokenDao;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    public void addNewToken(WxToken wxToken) {
        wxTokenDao.insert(wxToken);
    }

    public WxToken getWxTokenByAppId(String appId,String type) {
        return wxTokenDao.getWxTokenByAppId(appId,type);
    }

    public void updateWxToken(WxToken wxToken) {
        wxTokenDao.update(wxToken);
    }

    /**
     * 此处不考虑数据同步，仅测试redis的集成
     * @return
     */
    public List<WxToken> findAll() {

        //new一个redis的序列化器,提高key的可读性，至于value的可读性忽略
        RedisSerializer redisSerializer=new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        List<WxToken> wxTokenList= (List<WxToken>) redisTemplate.opsForValue().get("allWxTokens");
        if (wxTokenList==null){
            //既redis中没有该缓存
            wxTokenList= wxTokenDao.findAll();
            redisTemplate.opsForValue().set("allWxTokens",wxTokenList);
        }
        //如果不为空，既不用再查询数据库
        return wxTokenList;
    }

    /**
     * 这里写一个更新access_token的工具方法,便捷更新数据库中的token
     * @param wxToken 新的wxToken对象
     * @param appId
     * @param appSecret
     */
    public WxToken getNewTokenAndSaveOrUpdate(WxToken wxToken,String appId,String appSecret){
        //通过appid和secret获取到微信权限实体对象，包括凭证access_token和对应凭证过期时间,值得说明的是userId和openid看类详情
        Wxoauth2token wat= QiYeWeixinHelper.getOauth2AccessToken(appId,appSecret);
        String access_token=wat.getAccessToken();
        Integer expiresIn=wat.getExpiresIn();

        wxToken.setToken(access_token);
        Long date_=System.currentTimeMillis()+expiresIn*1000;//转换成毫秒计算过期时间
        Date date=new Date(date_);
        wxToken.setExpire(date);
        if (wxToken==null){
            //新凭证
            wxToken.setId(IdGenerate.uuid());//生成随机的uuid
            wxToken.setWxType("3");//企业微信
            wxToken.setAppId(appId);//企业微信appId
            this.addNewToken(wxToken);
        }else {
            //非新，更新
            this.updateWxToken(wxToken);
        }
        return wxToken;
    }


}
