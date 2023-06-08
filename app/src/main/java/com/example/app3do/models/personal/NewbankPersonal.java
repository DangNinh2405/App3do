package com.example.app3do.models.personal;

import com.google.gson.annotations.SerializedName;

public class NewbankPersonal {

    @SerializedName("bank_name")
    private String bankName;

    @SerializedName("account_name")
    private String accountName;

    @SerializedName("branch")
    private String branch;

    @SerializedName("account_number")
    private String accountNumber;

    @SerializedName("access_token")
    private String accessToken;


    public NewbankPersonal(String bankName, String accountName, String branch, String accountNumber, String accessToken) {
        this.bankName = bankName;
        this.accountName = accountName;
        this.branch = branch;
        this.accountNumber = accountNumber;
        this.accessToken = accessToken;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
