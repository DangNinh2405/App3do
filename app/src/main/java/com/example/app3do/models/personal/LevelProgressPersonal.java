package com.example.app3do.models.personal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LevelProgressPersonal implements Serializable {

    @SerializedName("level")
    private int level;

    @SerializedName("next_level")
    private int nextLevel;

    @SerializedName("current_level_commission")
    private int currentLevelCommission;

    @SerializedName("current_commission")
    private int currentCommission;

    @SerializedName("next_level_commission")
    private int nextLevelCommission;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(int nextLevel) {
        this.nextLevel = nextLevel;
    }

    public int getCurrentLevelCommission() {
        return currentLevelCommission;
    }

    public void setCurrentLevelCommission(int currentLevelCommission) {
        this.currentLevelCommission = currentLevelCommission;
    }

    public int getCurrentCommission() {
        return currentCommission;
    }

    public void setCurrentCommission(int currentCommission) {
        this.currentCommission = currentCommission;
    }

    public int getNextLevelCommission() {
        return nextLevelCommission;
    }

    public void setNextLevelCommission(int nextLevelCommission) {
        this.nextLevelCommission = nextLevelCommission;
    }
}
