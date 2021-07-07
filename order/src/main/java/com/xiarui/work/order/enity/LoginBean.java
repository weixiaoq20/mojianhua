package com.xiarui.work.order.enity;

import com.enjoy.network.beans.IBaseResponse;

public class LoginBean extends IBaseResponse {


    public Obj obj;

    public class Obj{

        /**
         * userId : 114827
         * account : 20069618
         * name : 刘全飞
         * userType : 1
         * userKey : appuserkey8374770662924fffb23756b1b40debfb
         * status : null
         */

        private String userId;
        private String account;
        private String name;
        private String userType;
        private String userKey;
        private Object status;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getUserKey() {
            return userKey;
        }

        public void setUserKey(String userKey) {
            this.userKey = userKey;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }
    }

}
