package com.song.export.model.bean;

import java.util.Date;

public class User {
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String truename;

    private String tel;

    private String address;

    private String email;

    private String lastoper;

    private String pwshow;

    private Date applydate;

    private Date createdate;

    private Date updatedate;

    private Date expiredate;

    private Integer status;

    private Integer colorschemaid;

    private Integer attentioncount;

    private String remark;

    public User(Long id, String username, String password, String nickname, String truename, String tel, String address, String email, String lastoper, String pwshow, Date applydate, Date createdate, Date updatedate, Date expiredate, Integer status, Integer colorschemaid, Integer attentioncount, String remark) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.truename = truename;
        this.tel = tel;
        this.address = address;
        this.email = email;
        this.lastoper = lastoper;
        this.pwshow = pwshow;
        this.applydate = applydate;
        this.createdate = createdate;
        this.updatedate = updatedate;
        this.expiredate = expiredate;
        this.status = status;
        this.colorschemaid = colorschemaid;
        this.attentioncount = attentioncount;
        this.remark = remark;
    }

    public User() {
        super();
    }

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastoper() {
        return lastoper;
    }

    public void setLastoper(String lastoper) {
        this.lastoper = lastoper;
    }

    public String getPwshow() {
        return pwshow;
    }

    public void setPwshow(String pwshow) {
        this.pwshow = pwshow;
    }

    public Date getApplydate() {
        return applydate;
    }

    public void setApplydate(Date applydate) {
        this.applydate = applydate;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Date getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(Date expiredate) {
        this.expiredate = expiredate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getColorschemaid() {
        return colorschemaid;
    }

    public void setColorschemaid(Integer colorschemaid) {
        this.colorschemaid = colorschemaid;
    }

    public Integer getAttentioncount() {
        return attentioncount;
    }

    public void setAttentioncount(Integer attentioncount) {
        this.attentioncount = attentioncount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}