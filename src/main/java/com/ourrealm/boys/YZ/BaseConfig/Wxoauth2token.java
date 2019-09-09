package com.ourrealm.boys.YZ.BaseConfig;

import java.util.Date;

/**
 * 微信权限实体类
 */
public class Wxoauth2token {
    private String accessToken; //网页授权接口调用凭证
    private Integer expiresIn;  //凭证有效时长
    private String scope;
    private String refreshToken;		// 用于刷新凭证
    private Date updateTime;		// 开始日期

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
