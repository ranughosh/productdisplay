package products.testproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ghoshr on 11/6/2017.
 */

public class ResponseObj {
    @SerializedName("products")
    @Expose
    private ArrayList<Product> mProductList;
    @SerializedName("totalProducts")
    @Expose
    private Integer mTotalProducts;
    @SerializedName("pageNumber")
    @Expose
    private Integer mPageName;
    @SerializedName("pageSize")
    @Expose
    private Integer mPageSize;
    @SerializedName("status")
    @Expose
    private Integer mStatus;
    @SerializedName("kind")
    @Expose
    private String mKind;
    @SerializedName("etag")
    @Expose
    private String mEtag;
    @SerializedName("error")
    @Expose
    private String mError;

    public String getError() {
        return mError;
    }

    public void setError(String mError) {
        this.mError = mError;
    }

    public ArrayList<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(ArrayList<Product> mProductList) {
        this.mProductList = mProductList;
    }

    public Integer getTotalProducts() {
        return mTotalProducts;
    }

    public void setTotalProducts(Integer mTotalProducts) {
        this.mTotalProducts = mTotalProducts;
    }

    public Integer getPageName() {
        return mPageName;
    }

    public void setPageName(Integer mPageName) {
        this.mPageName = mPageName;
    }

    public Integer getPageSize() {
        return mPageSize;
    }

    public void setPageSize(Integer mPageSize) {
        this.mPageSize = mPageSize;
    }

    public Integer getStatus() {
        return mStatus;
    }

    public void setStatus(Integer mStatus) {
        this.mStatus = mStatus;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String mKind) {
        this.mKind = mKind;
    }

    public String getEtag() {
        return mEtag;
    }

    public void setEtag(String mEtag) {
        this.mEtag = mEtag;
    }
}
