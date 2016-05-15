package com.example.sanyam.rbs;

import java.util.Date;

/**
 * Created by sanyam on 14/5/16.
 */
public class InfoInt {
    private String from;
    private String to;
    private String type;
    private int duration;
    private int balance;
    private int mcc;
    private int mnc;
    private int cid;
    private int lac;
    private Date date;

    public InfoInt(String from, String to, String type, int duration, int balance, int mcc, int mnc, int cid, int lac, Date date) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.duration = duration;
        this.balance = balance;
        this.mcc = mcc;
        this.mnc = mnc;
        this.cid = cid;
        this.lac = lac;
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getMcc() {
        return mcc;
    }

    public void setMcc(int mcc) {
        this.mcc = mcc;
    }

    public int getMnc() {
        return mnc;
    }

    public void setMnc(int mnc) {
        this.mnc = mnc;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getLac() {
        return lac;
    }

    public void setLac(int lac) {
        this.lac = lac;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
