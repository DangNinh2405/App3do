package com.example.app3do.models.order;

import com.example.app3do.models.product.Pagination;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyOrder {
    @SerializedName("meta")
    private MetaOrder meta;

    @SerializedName("data")
    private List<DataOrder> data;

    public MetaOrder getMeta() {
        return meta;
    }

    public void setMeta(MetaOrder meta) {
        this.meta = meta;
    }

    public List<DataOrder> getData() {
        return data;
    }

    public void setData(List<DataOrder> data) {
        this.data = data;
    }
}
