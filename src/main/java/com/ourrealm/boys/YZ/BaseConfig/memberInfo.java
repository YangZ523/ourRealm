package com.ourrealm.boys.YZ.BaseConfig;

/**
 * 通过code和access_token获取的成员信息，userId或者openid
 */
public class memberInfo {
    private Integer errcode;//返回码
    private String errmsg;//对返回码描述内容
    private String UserId;
    private String OpenId;
    private String DeviceId;//手机设备号

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String openId) {
        OpenId = openId;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }
}
