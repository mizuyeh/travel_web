package com.cyx.entity;

import java.io.Serializable;

/**
 * @Description
 * @date 2021/2/27
 */
public class Traveler implements Serializable {
    private String id;
    private String travelerName;
    private String sex;
    private String phoneNum;
    private Integer credentialsType;
    private String credentialsTypeStr;
    private String credentialsNum;
    private Integer travelerType;
    private String travelerTypeStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTravelerName() {
        return travelerName;
    }

    public void setTravelerName(String travelerName) {
        this.travelerName = travelerName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(Integer credentialsType) {
        this.credentialsType = credentialsType;
    }

    public String getCredentialsTypeStr() {
        return credentialsTypeStr;
    }

    public void setCredentialsTypeStr(String credentialsTypeStr) {
        this.credentialsTypeStr = credentialsTypeStr;
    }

    public String getCredentialsNum() {
        return credentialsNum;
    }

    public void setCredentialsNum(String credentialsNum) {
        this.credentialsNum = credentialsNum;
    }

    public Integer getTravelerType() {
        return travelerType;
    }

    public void setTravelerType(Integer travelerType) {
        this.travelerType = travelerType;
    }

    public String getTravelerTypeStr() {
        return travelerTypeStr;
    }

    public void setTravelerTypeStr(String travelerTypeStr) {
        this.travelerTypeStr = travelerTypeStr;
    }

    @Override
    public String toString() {
        return "Traveler{" +
                "id='" + id + '\'' +
                ", travelerName='" + travelerName + '\'' +
                ", sex='" + sex + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", credentialsType=" + credentialsType +
                ", credentialsTypeStr='" + credentialsTypeStr + '\'' +
                ", credentialsNum='" + credentialsNum + '\'' +
                ", travelerType=" + travelerType +
                ", travelerTypeStr='" + travelerTypeStr + '\'' +
                '}';
    }
}
