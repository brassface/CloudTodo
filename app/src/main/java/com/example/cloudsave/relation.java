package com.example.cloudsave;

import cn.bmob.v3.BmobObject;

public class relation extends BmobObject {
    private String up;
    private String down;
    private String called;
    relation()
    {}
    relation(String up,String down,String called){
        this.up=up;
        this.down=down;
        this.called=called;
    }
    public String getUp() { return up; }
    public void setUp(String up) { this.up = up; }
    public String getDown() { return down; }
    public void setDown(String down) { this.down = down; }
    public String getCalled() {
        return called;
    }
    public void setCalled(String called) {
        this.called = called;
    }
}
