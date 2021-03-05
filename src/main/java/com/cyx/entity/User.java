package com.cyx.entity;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @date 2021/3/3
 */
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String salt;
    private List<Long> role_ids;
    private List<String> roleDesc;
    private Boolean locked = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCredentialsSalt() {
        return username + salt;
    }

    public List<Long> getRole_ids() {
        if(role_ids == null) {
            role_ids = new ArrayList<>();
        }
        return role_ids;
    }

    public void setRole_ids(List<Long> role_ids) {
        this.role_ids = role_ids;
    }

    public String getRoleIdsStr() {
        if(CollectionUtils.isEmpty(role_ids)) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        for(Long role_id : role_ids) {
            s.append(role_id);
            s.append(",");
        }
        return s.toString();
    }

    /**
     * 取role_ids时，由于对应的数据库字段类型为varchar，
     * 先用setRoleIdsStr方法设置role_ids的值，
     * 同时执行响应sql时，应将字段role_ids as roleIdsStr
     * @Param [roleIdsStr]
     * @Return void
     */
    public void setRoleIdsStr(String roleIdsStr) {
        if(roleIdsStr.isEmpty()) {
            return;
        }
        String[] roleIdsStrs = roleIdsStr.split(",");
        for (String roleIdStr : roleIdsStrs) {
            if(roleIdStr.isEmpty()) {
                return;
            }
            getRole_ids().add(Long.valueOf(roleIdStr));
        }
    }

    public List<String> getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(List<String> roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", role_ids=" + role_ids +
                ", locked=" + locked +
                '}';
    }
}
