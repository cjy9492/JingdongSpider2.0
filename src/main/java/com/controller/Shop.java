package com.controller;

/**
 * Created by hspcadmin on 2016/8/17.
 */
public class Shop {
    private String id;
    private String shopid;
    private String name;
    private String price;
    private String myprice;
    private boolean flag;
    private String type;
    private String addday;
    private String sendday;
    private String inprice;
    private String lowprice;
    private String emailflag;

    public String getEmailflag() {
        return emailflag;
    }

    public void setEmailflag(String emailflag) {
        this.emailflag = emailflag;
    }

    public String getLowprice() {
        return lowprice;
    }

    public void setLowprice(String lowprice) {
        this.lowprice = lowprice;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getInprice() {
        return inprice;
    }

    public void setInprice(String inprice) {
        this.inprice = inprice;
    }

    public String getSendday() {
        return sendday;
    }

    public void setSendday(String sendday) {
        this.sendday = sendday;
    }

    public String getAddday() {
        return addday;
    }

    public void setAddday(String addday) {
        this.addday = addday;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMyprice() {
        return myprice;
    }

    public void setMyprice(String myprice) {
        this.myprice = myprice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

}
