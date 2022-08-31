package com.example.cloudsave;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private String name;
    private String pw;

    public String getPw() {
        return pw;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
