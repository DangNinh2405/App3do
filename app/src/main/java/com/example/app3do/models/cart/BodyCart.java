package com.example.app3do.models.cart;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BodyCart implements Serializable {

    @SerializedName("meta")
    private MeTaCart meTaCart;

    @SerializedName("data")
    private List<DataCart> dataCart;

    public MeTaCart getMeTaCart() {
        return meTaCart;
    }

    public void setMeTaCart(MeTaCart meTaCart) {
        this.meTaCart = meTaCart;
    }

    public List<DataCart> getDataCart() {
        return dataCart;
    }

    public void setDataCart(List<DataCart> dataCart) {
        this.dataCart = dataCart;
    }
}
