package com.ourrealm.boys.YZ.Models;

import org.hibernate.validator.constraints.Length;

public class User {

    private int id;
    private String userName;
    private char userSex;
    private int userAge;
    private String userAdress;
    private String userLoginName;
    private String userLoginPsWord;
    private String userPhone;
    private String userEmail;
    private String userLogo;
    private String userSchool;
    private String userCity;
    private String userProvince;
    private String userWeixin;

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getUserLoginPsWord() {
        return userLoginPsWord;
    }

    public void setUserLoginPsWord(String userLoginPsWord) {
        this.userLoginPsWord = userLoginPsWord;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserLogo() {
        return userLogo;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
    }

    public String getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserProvince() {
        return userProvince;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince;
    }

    public String getUserWeixin() {
        return userWeixin;
    }

    public void setUserWeixin(String userWeixin) {
        this.userWeixin = userWeixin;
    }

    @Length(min =0,max = 64,message = "主键id的长度介于0-64之间")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Length(min = 0,max = 64,message = "用户昵称的长度介于0和64之间")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Length(min = 0,max =1,message = "用户性别的字段值长度介于0和1之间")
    public char getUserSex() {
        return userSex;
    }

    public void setUserSex(char userSex) {
        this.userSex = userSex;
    }

    @Length(min = 0,max = 4,message = "用户年龄的字段值长度介于0和4之间")
    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    @Length(min = 0,max = 225,message = "用户地址值的长度介于0和225之间")
    public String getUserAdress() {
        return userAdress;
    }

    public void setUserAdress(String userAdress) {
        this.userAdress = userAdress;
    }


}
