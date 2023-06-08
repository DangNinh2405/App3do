package com.example.app3do.models.personal;

import com.google.gson.annotations.SerializedName;

public class MetaPersonal {

    @SerializedName("direct")
    private DirectPersonal direct;

    @SerializedName("users_all")
    private DirectPersonal usersAll;

    public DirectPersonal getDirect() {
        return direct;
    }

    public void setDirect(DirectPersonal direct) {
        this.direct = direct;
    }

    public DirectPersonal getUsersAll() {
        return usersAll;
    }

    public void setUsersAll(DirectPersonal usersAll) {
        this.usersAll = usersAll;
    }
}
