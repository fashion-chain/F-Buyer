package com.hottop.backstage.bguser.enums;

public class EBgUser {

    public enum EBgUserStatus {
        OK("1"), DISABLED("0");

        public String status = "0";
        EBgUserStatus (String status){this.status = status;}
    }
}
