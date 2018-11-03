package science.mrcuijt.loh.entity;

import java.util.Date;

/**
 * 登录信息表实体
 * 
 * @author Administrator
 *
 */
public class LoginInfo {
    /**
     * 
     */
    private Integer loginInfoId;

    /**
     *
     */
    private Date gmtCreate;

    /**
     *
     */
    private Date gmtModified;

    /**
     *
     */
    private String loginAccount;

    /**
     *
     */
    private String loginPassword;

    /**
     *
     */
    private Integer userInfoId;

    /**
     *
     */
    private Date currentLoginTime;

    /**
     *
     */
    private Date lastLoginTime;

    /**
     *
     */
    private String loginIp;

    /**
     *
     */
    public Integer getLoginInfoId() {
        return loginInfoId;
    }

    /**
     *
     */
    public void setLoginInfoId(Integer loginInfoId) {
        this.loginInfoId = loginInfoId;
    }

    /**
     *
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     *
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     *
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     *
     */
    public String getLoginAccount() {
        return loginAccount;
    }

    /**
     *
     */
    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    /**
     *
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     *
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    /**
     *
     */
    public Integer getUserInfoId() {
        return userInfoId;
    }

    /**
     *
     */
    public void setUserInfoId(Integer userInfoId) {
        this.userInfoId = userInfoId;
    }

    /**
     *
     */
    public Date getCurrentLoginTime() {
        return currentLoginTime;
    }

    /**
     *
     */
    public void setCurrentLoginTime(Date currentLoginTime) {
        this.currentLoginTime = currentLoginTime;
    }

    /**
     *
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     *
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     *
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     *
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
}