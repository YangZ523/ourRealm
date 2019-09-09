package com.ourrealm.boys.YZ.utils;

import com.alibaba.fastjson.JSONObject;
import com.ourrealm.boys.YZ.BaseConfig.WeixinConfig;
import com.ourrealm.boys.YZ.BaseConfig.Wxoauth2token;
import com.ourrealm.boys.YZ.BaseConfig.memberInfo;
import static com.ourrealm.boys.YZ.utils.CommonUtil.httpsRequest;

public class QiYeWeixinHelper {
    /**
     * 获取网页授权凭证
     *
     * @param appId     公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @return WeixinAouth2Token
     */
    public static Wxoauth2token getOauth2AccessToken(String appId, String appSecret) {
        Wxoauth2token wat = null;
        // 拼接请求地址
        String requestUrl =WeixinConfig.GET_ACCESS_TOKEN;
        requestUrl = requestUrl.replace("ID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        // 获取网页授权凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                wat = new Wxoauth2token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(7200);
            } catch (Exception e) {
                wat = null;
            }
        }
        return wat;
    }


    /**
     * 通过网页授权获取成员信息
     *
     * @param access_token 网页授权接口调用凭证
     * @param code      登录code
     * @return memberInfo
     */
    public static memberInfo getMemberInfo(String access_token, String code) {
        memberInfo memberInfo=null;
        //拼接请求地址
        String requestUrl=WeixinConfig.GET_USER_INFO;
        requestUrl= requestUrl.replace("ACCESS_TOKEN",access_token);
        requestUrl=requestUrl.replace("CODE",code);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (null!=jsonObject){
            try {
                memberInfo=new memberInfo();
                if (Integer.parseInt(jsonObject.getString("errcode"))==0&&jsonObject.getString("errmsg").equals("ok")){
                    //返回码成功
                    memberInfo.setErrcode(0);
                    memberInfo.setErrmsg(jsonObject.getString("errmsg"));
                    memberInfo.setDeviceId(jsonObject.getString("DeviceId"));


                    if (jsonObject.getString("OpenId")!=null&&!jsonObject.getString("OpenId").equals("")){
                        //说明返回的是第二种结果，不是内部成员
                        memberInfo.setOpenId(jsonObject.getString("OpenId"));
                        return memberInfo;
                    }else if (jsonObject.getString("UserId")!=null&&!jsonObject.getString("UserId").equals("")){
                        //说明是内部成员，可以通过userid获取成员信息
                        memberInfo.setUserId(jsonObject.getString("UserId"));
                        return memberInfo;
                    }else {
                        //返回出错
                        return null;
                    }
                }else {
                    return null;
                }
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }
}
