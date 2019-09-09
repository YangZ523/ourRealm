package com.ourrealm.boys.YZ.Models;

import java.io.Serializable;
import java.util.Date;

public class WxToken implements Serializable {
    private String id;//id主键
    private String appId;//微信端的appId
    private String token;//access_token接口凭证
    private Date expire;//token过期时间

    public Date getExpire() {
        return expire;
    }
    public void setExpire(Date expire) {
        this.expire = expire;
    }
    private String wxType;//微信类型（1微信公众号，2微信小程序，3企业微信）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWxType() {
        return wxType;
    }

    public void setWxType(String wxType) {
        this.wxType = wxType;
    }
}
