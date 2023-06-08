package com.example.app3do.models.personal;

import com.google.gson.annotations.SerializedName;

public class BanksPersonal {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("account_name")
    private String accountName;

    @SerializedName("account_number")
    private String accountNumber;

    @SerializedName("branch")
    private String branch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
