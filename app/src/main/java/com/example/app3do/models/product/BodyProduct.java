package com.example.app3do.models.product;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BodyProduct {

    @SerializedName("meta")
    private MetaProduct metaProduct;

    @SerializedName("data")
    private List<DataProduct> dataProduct;

    public MetaProduct getMetaProduct() {
        return metaProduct;
    }


    public void setMetaProduct(MetaProduct metaProduct) {
        this.metaProduct = metaProduct;
    }

    public List<DataProduct> getDataProduct() {
        return dataProduct;
    }

    public void setDataProduct(List<DataProduct> dataProduct) {
        this.dataProduct = dataProduct;
    }
}
