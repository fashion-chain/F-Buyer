package com.hottop.backstage.application.enums;

public class EBgUser {

    public enum EBgUserStatus {
        ok("0"), disable("1");

        String status;
        EBgUserStatus (String status){this.status = status;}

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
