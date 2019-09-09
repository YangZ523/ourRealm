/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  CodeEnum.java
 * PACKAGE      :  com.anssy.inter
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.ourrealm.boys.YZ.BaseConfig;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 */
public enum CodeEnum {

    FAILURE("操作失败!", ResultStatus.RESULT_STATUS_FAILURE),
    SUCCESS("操作成功!", ResultStatus.RESULT_STATUS_SUCCESS);

    private String msg;
    private int code;

    // 构造方法
    private CodeEnum(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (CodeEnum c : CodeEnum.values()) {
            if (c.getCode() == code) {
                return c.msg;
            }
        }
        return "";
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}