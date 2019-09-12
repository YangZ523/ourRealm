package com.ourrealm.boys.YZ.BaseConfig;

/**
 * @author yangz
 * @date 2019-7-1
 * 描述：调用微信接口的相公静态资源获取工具类
 */
public class ApiUtils {

    //拼接code换取openid调用微信的接口url（auth.code2Session）
    public static String getWebAccess(String CODE){
        return String.format(ResourceUtil.getConfigByName("wx.webAccessTokenhttps"),
                ResourceUtil.getConfigByName("wx.appId"),
                ResourceUtil.getConfigByName("wx.secret"),CODE);
    }

}
