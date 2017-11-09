package products.testproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by ghoshr on 11/6/2017.
 */

public class Product implements Parcelable {

    @SerializedName("productId")
    @Expose
    private String mProductId;
    @SerializedName("productName")
    @Expose
    private String mProductName;
    @SerializedName("shortDescription")
    @Expose
    private String mProductShortDesc;
    @SerializedName("longDescription")
    @Expose
    private String mProductLongDesc;
    @SerializedName("price")
    @Expose
    private String mPrice;
    @SerializedName("productImage")
    @Expose
    private String mProductImage;
    @SerializedName("reviewRating")
    @Expose
    private Float mReviewRating;
    @SerializedName("reviewCount")
    @Expose
    private Integer mReviewCount;
    @SerializedName("inStock")
    @Expose
    private boolean mInStock;

    public String getProductId() {
        return mProductId;
    }

    public void setProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public String getProductShortDesc() {
        return mProductShortDesc;
    }

    public void setProductShortDesc(String mProductShortDesc) {
        this.mProductShortDesc = mProductShortDesc;
    }

    public String getProductLongDesc() {
        return mProductLongDesc;
    }

    public void setProductLongDesc(String mProductLongDesc) {
        this.mProductLongDesc = mProductLongDesc;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getProductImage() {
        return mProductImage;
    }

    public void setProductImage(String mProductImage) {
        this.mProductImage = mProductImage;
    }

    public Float getReviewRating() {
        return mReviewRating;
    }

    public void setReviewRating(Float mReviewRating) {
        this.mReviewRating = mReviewRating;
    }

    public Integer getReviewCount() {
        return mReviewCount;
    }

    public void setReviewCount(Integer mReviewCount) {
        this.mReviewCount = mReviewCount;
    }

    public boolean isInStock() {
        return mInStock;
    }

    public void setIsInStock(boolean mIsInStock) {
        this.mInStock = mIsInStock;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
            out.writeString(mProductId);
            out.writeString(mProductName);
            out.writeString(mProductShortDesc);
            out.writeString(mProductLongDesc);
            out.writeString(mPrice);
            out.writeString(mProductImage);
            out.writeFloat(mReviewRating);
            out.writeInt(mReviewCount);
            out.writeInt(mInStock?1:0);
    }

    public Product(Parcel in) {
          mProductId=in.readString();
          mProductName=in.readString();
          mProductShortDesc=in.readString();
          mProductLongDesc=in.readString();
          mPrice=in.readString();
          mProductImage=in.readString();
          mReviewRating=in.readFloat();
          mReviewCount=in.readInt();
          mInStock=in.readInt()==1?true:false;
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Product createFromParcel(Parcel in) {
                    return new Product(in);
                }

                public Product[] newArray(int size) {
                    return new Product[size];
                }
            };


}
