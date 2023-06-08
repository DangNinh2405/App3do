package com.example.app3do.models.personal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DataPersonal implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("gender")
    private String gender;

    @SerializedName("birthdays")
    private String birthdays;

    @SerializedName("address")
    private String address;

    @SerializedName("email")
    private String email;

    @SerializedName("identity")
    private IdentityPersonal identity;

    @SerializedName("level_config")
    private int levelConfig;

    @SerializedName("level")
    private int level;

    @SerializedName("level_progress")
    private LevelProgressPersonal levelProgress;

    @SerializedName("total_order_success")
    private int totalOrderSuccess;

    @SerializedName("owner_commission")
    private int ownerCommission;

    @SerializedName("addresses")
    private List<AddressesPersonal> addresses;

    @SerializedName("banks")
    private List<BanksPersonal> banks;

    @SerializedName("total_ord_amount")
    private int totalOrdAmount;

    public int getTotalOrdAmount() {
        return totalOrdAmount;
    }

    public void setTotalOrdAmount(int totalOrdAmount) {
        this.totalOrdAmount = totalOrdAmount;
    }

    public List<AddressesPersonal> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressesPersonal> addresses) {
        this.addresses = addresses;
    }

    public List<BanksPersonal> getBanks() {
        return banks;
    }

    public void setBanks(List<BanksPersonal> banks) {
        this.banks = banks;
    }

    public void setLevelConfig(int levelConfig) {
        this.levelConfig = levelConfig;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdays() {
        return birthdays;
    }

    public void setBirthdays(String birthdays) {
        this.birthdays = birthdays;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public IdentityPersonal getIdentity() {
        return identity;
    }

    public void setIdentity(IdentityPersonal identity) {
        this.identity = identity;
    }

    public int getLevelConfig() {
        return levelConfig;
    }

    public void setLevel_config(int levelConfig) {
        this.levelConfig = levelConfig;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public LevelProgressPersonal getLevelProgress() {
        return levelProgress;
    }

    public void setLevelProgress(LevelProgressPersonal levelProgress) {
        this.levelProgress = levelProgress;
    }

    public int getTotalOrderSuccess() {
        return totalOrderSuccess;
    }

    public void setTotalOrderSuccess(int totalOrderSuccess) {
        this.totalOrderSuccess = totalOrderSuccess;
    }

    public int getOwnerCommission() {
        return ownerCommission;
    }

    public void setOwnerCommission(int ownerCommission) {
        this.ownerCommission = ownerCommission;
    }
}
