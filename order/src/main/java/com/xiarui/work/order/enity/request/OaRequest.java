package com.xiarui.work.order.enity.request;

public class OaRequest {
    private String userName;
    private String password;
    private String loginName;
    private String userAgentFrom;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setUserAgentFrom(String userAgentFrom) {
        this.userAgentFrom = userAgentFrom;
    }

    public String getUserAgentFrom() {
        return this.userAgentFrom;
    }

}
