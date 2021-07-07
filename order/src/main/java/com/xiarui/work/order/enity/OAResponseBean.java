package com.xiarui.work.order.enity;

public class OAResponseBean {


    /**
     * bindingUser : {"loginState":"logging","sessionId":"0A2D3C4AD610838771AF304E2CAB63A3","id":8580355068896365123,"securityKey":0,"loginName":"20069618","password":null,"name":"刘全飞","loginAccount":670869647114347,"loginAccountName":"阿坝师范学院","loginAccountShortName":"单位","nodeIndex":"","departmentId":-1491708629728345497,"levelId":8970397511570965227,"postId":-1814760484229265081,"accountId":670869647114347,"remoteAddr":"125.71.154.238","userAgentFrom":"weixin","externalType":0,"administrator":false,"groupAdmin":false,"superAdmin":false,"systemAdmin":false,"auditAdmin":false,"platformAdmin":false,"locale":"zh_CN","loginTimestamp":1625231348700,"loginLogId":9181010801280372298,"skin":"default","userSSOFrom":null,"browser":null,"timeZone":"Asia/Shanghai","canSendSMS":false,"etagRandom":"-1221764238","vjoinMember":false,"internal":true,"admin":false,"v5Member":true,"loginSign":8,"guest":false,"fromM1":false,"defaultGuest":false,"screenGuest":false}
     * id : 2fb1a97e-b583-4d16-a6a9-c8640be14ed0
     */

    private BindingUserBean bindingUser;
    private String id;

    public BindingUserBean getBindingUser() {
        return bindingUser;
    }

    public void setBindingUser(BindingUserBean bindingUser) {
        this.bindingUser = bindingUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OAResponseBean{" +
                "bindingUser=" + bindingUser +
                ", id='" + id + '\'' +
                '}';
    }

    public static class BindingUserBean {
        /**
         * loginState : logging
         * sessionId : 0A2D3C4AD610838771AF304E2CAB63A3
         * id : 8580355068896365123
         * securityKey : 0
         * loginName : 20069618
         * password : null
         * name : 刘全飞
         * loginAccount : 670869647114347
         * loginAccountName : 阿坝师范学院
         * loginAccountShortName : 单位
         * nodeIndex :
         * departmentId : -1491708629728345497
         * levelId : 8970397511570965227
         * postId : -1814760484229265081
         * accountId : 670869647114347
         * remoteAddr : 125.71.154.238
         * userAgentFrom : weixin
         * externalType : 0
         * administrator : false
         * groupAdmin : false
         * superAdmin : false
         * systemAdmin : false
         * auditAdmin : false
         * platformAdmin : false
         * locale : zh_CN
         * loginTimestamp : 1625231348700
         * loginLogId : 9181010801280372298
         * skin : default
         * userSSOFrom : null
         * browser : null
         * timeZone : Asia/Shanghai
         * canSendSMS : false
         * etagRandom : -1221764238
         * vjoinMember : false
         * internal : true
         * admin : false
         * v5Member : true
         * loginSign : 8
         * guest : false
         * fromM1 : false
         * defaultGuest : false
         * screenGuest : false
         */

        private String loginState;
        private String sessionId;
        private long id;
        private int securityKey;
        private String loginName;
        private Object password;
        private String name;
        private long loginAccount;
        private String loginAccountName;
        private String loginAccountShortName;
        private String nodeIndex;
        private long departmentId;
        private long levelId;
        private long postId;
        private long accountId;
        private String remoteAddr;
        private String userAgentFrom;
        private int externalType;
        private boolean administrator;
        private boolean groupAdmin;
        private boolean superAdmin;
        private boolean systemAdmin;
        private boolean auditAdmin;
        private boolean platformAdmin;
        private String locale;
        private long loginTimestamp;
        private long loginLogId;
        private String skin;
        private Object userSSOFrom;
        private Object browser;
        private String timeZone;
        private boolean canSendSMS;
        private String etagRandom;
        private boolean vjoinMember;
        private boolean internal;
        private boolean admin;
        private boolean v5Member;
        private int loginSign;
        private boolean guest;
        private boolean fromM1;
        private boolean defaultGuest;
        private boolean screenGuest;

        public String getLoginState() {
            return loginState;
        }

        public void setLoginState(String loginState) {
            this.loginState = loginState;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getSecurityKey() {
            return securityKey;
        }

        public void setSecurityKey(int securityKey) {
            this.securityKey = securityKey;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getLoginAccount() {
            return loginAccount;
        }

        public void setLoginAccount(long loginAccount) {
            this.loginAccount = loginAccount;
        }

        public String getLoginAccountName() {
            return loginAccountName;
        }

        public void setLoginAccountName(String loginAccountName) {
            this.loginAccountName = loginAccountName;
        }

        public String getLoginAccountShortName() {
            return loginAccountShortName;
        }

        public void setLoginAccountShortName(String loginAccountShortName) {
            this.loginAccountShortName = loginAccountShortName;
        }

        public String getNodeIndex() {
            return nodeIndex;
        }

        public void setNodeIndex(String nodeIndex) {
            this.nodeIndex = nodeIndex;
        }

        public long getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(long departmentId) {
            this.departmentId = departmentId;
        }

        public long getLevelId() {
            return levelId;
        }

        public void setLevelId(long levelId) {
            this.levelId = levelId;
        }

        public long getPostId() {
            return postId;
        }

        public void setPostId(long postId) {
            this.postId = postId;
        }

        public long getAccountId() {
            return accountId;
        }

        public void setAccountId(long accountId) {
            this.accountId = accountId;
        }

        public String getRemoteAddr() {
            return remoteAddr;
        }

        public void setRemoteAddr(String remoteAddr) {
            this.remoteAddr = remoteAddr;
        }

        public String getUserAgentFrom() {
            return userAgentFrom;
        }

        public void setUserAgentFrom(String userAgentFrom) {
            this.userAgentFrom = userAgentFrom;
        }

        public int getExternalType() {
            return externalType;
        }

        public void setExternalType(int externalType) {
            this.externalType = externalType;
        }

        public boolean isAdministrator() {
            return administrator;
        }

        public void setAdministrator(boolean administrator) {
            this.administrator = administrator;
        }

        public boolean isGroupAdmin() {
            return groupAdmin;
        }

        public void setGroupAdmin(boolean groupAdmin) {
            this.groupAdmin = groupAdmin;
        }

        public boolean isSuperAdmin() {
            return superAdmin;
        }

        public void setSuperAdmin(boolean superAdmin) {
            this.superAdmin = superAdmin;
        }

        public boolean isSystemAdmin() {
            return systemAdmin;
        }

        public void setSystemAdmin(boolean systemAdmin) {
            this.systemAdmin = systemAdmin;
        }

        public boolean isAuditAdmin() {
            return auditAdmin;
        }

        public void setAuditAdmin(boolean auditAdmin) {
            this.auditAdmin = auditAdmin;
        }

        public boolean isPlatformAdmin() {
            return platformAdmin;
        }

        public void setPlatformAdmin(boolean platformAdmin) {
            this.platformAdmin = platformAdmin;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public long getLoginTimestamp() {
            return loginTimestamp;
        }

        public void setLoginTimestamp(long loginTimestamp) {
            this.loginTimestamp = loginTimestamp;
        }

        public long getLoginLogId() {
            return loginLogId;
        }

        public void setLoginLogId(long loginLogId) {
            this.loginLogId = loginLogId;
        }

        public String getSkin() {
            return skin;
        }

        public void setSkin(String skin) {
            this.skin = skin;
        }

        public Object getUserSSOFrom() {
            return userSSOFrom;
        }

        public void setUserSSOFrom(Object userSSOFrom) {
            this.userSSOFrom = userSSOFrom;
        }

        public Object getBrowser() {
            return browser;
        }

        public void setBrowser(Object browser) {
            this.browser = browser;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public boolean isCanSendSMS() {
            return canSendSMS;
        }

        public void setCanSendSMS(boolean canSendSMS) {
            this.canSendSMS = canSendSMS;
        }

        public String getEtagRandom() {
            return etagRandom;
        }

        public void setEtagRandom(String etagRandom) {
            this.etagRandom = etagRandom;
        }

        public boolean isVjoinMember() {
            return vjoinMember;
        }

        public void setVjoinMember(boolean vjoinMember) {
            this.vjoinMember = vjoinMember;
        }

        public boolean isInternal() {
            return internal;
        }

        public void setInternal(boolean internal) {
            this.internal = internal;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public boolean isV5Member() {
            return v5Member;
        }

        public void setV5Member(boolean v5Member) {
            this.v5Member = v5Member;
        }

        public int getLoginSign() {
            return loginSign;
        }

        public void setLoginSign(int loginSign) {
            this.loginSign = loginSign;
        }

        public boolean isGuest() {
            return guest;
        }

        public void setGuest(boolean guest) {
            this.guest = guest;
        }

        public boolean isFromM1() {
            return fromM1;
        }

        public void setFromM1(boolean fromM1) {
            this.fromM1 = fromM1;
        }

        public boolean isDefaultGuest() {
            return defaultGuest;
        }

        public void setDefaultGuest(boolean defaultGuest) {
            this.defaultGuest = defaultGuest;
        }

        public boolean isScreenGuest() {
            return screenGuest;
        }

        public void setScreenGuest(boolean screenGuest) {
            this.screenGuest = screenGuest;
        }


        @Override
        public String toString() {
            return "BindingUserBean{" +
                    "loginState='" + loginState + '\'' +
                    ", sessionId='" + sessionId + '\'' +
                    ", id=" + id +
                    ", securityKey=" + securityKey +
                    ", loginName='" + loginName + '\'' +
                    ", password=" + password +
                    ", name='" + name + '\'' +
                    ", loginAccount=" + loginAccount +
                    ", loginAccountName='" + loginAccountName + '\'' +
                    ", loginAccountShortName='" + loginAccountShortName + '\'' +
                    ", nodeIndex='" + nodeIndex + '\'' +
                    ", departmentId=" + departmentId +
                    ", levelId=" + levelId +
                    ", postId=" + postId +
                    ", accountId=" + accountId +
                    ", remoteAddr='" + remoteAddr + '\'' +
                    ", userAgentFrom='" + userAgentFrom + '\'' +
                    ", externalType=" + externalType +
                    ", administrator=" + administrator +
                    ", groupAdmin=" + groupAdmin +
                    ", superAdmin=" + superAdmin +
                    ", systemAdmin=" + systemAdmin +
                    ", auditAdmin=" + auditAdmin +
                    ", platformAdmin=" + platformAdmin +
                    ", locale='" + locale + '\'' +
                    ", loginTimestamp=" + loginTimestamp +
                    ", loginLogId=" + loginLogId +
                    ", skin='" + skin + '\'' +
                    ", userSSOFrom=" + userSSOFrom +
                    ", browser=" + browser +
                    ", timeZone='" + timeZone + '\'' +
                    ", canSendSMS=" + canSendSMS +
                    ", etagRandom='" + etagRandom + '\'' +
                    ", vjoinMember=" + vjoinMember +
                    ", internal=" + internal +
                    ", admin=" + admin +
                    ", v5Member=" + v5Member +
                    ", loginSign=" + loginSign +
                    ", guest=" + guest +
                    ", fromM1=" + fromM1 +
                    ", defaultGuest=" + defaultGuest +
                    ", screenGuest=" + screenGuest +
                    '}';
        }
    }
}
