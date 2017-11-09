package products.testproject.presenter;

import android.util.Log;

import java.util.ArrayList;

import products.testproject.model.Product;
import products.testproject.view.RecyclerRowView;

/**
 * Created by ghoshr on 11/7/2017.
 */

public class DetailPresenter {

    private RecyclerRowView mDetailView;
    private ArrayList<Product> productList;
    private String TAG="DetailPresenter";

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }
    public DetailPresenter(RecyclerRowView detailView){
        mDetailView=detailView;
    }

    public void updateUI(int position){

        if(productList!=null && productList.size()>position) {
            Log.d(TAG,"size:"+productList.size()+":"+position);
            Product currPrd = productList.get(position);
            Log.d(TAG,currPrd.getProductName());
            mDetailView.setImage(currPrd.getProductImage());
            mDetailView.setProductName(currPrd.getProductName());
            mDetailView.setLongDesc(currPrd.getProductLongDesc());
            mDetailView.setPrice(currPrd.getPrice());
            mDetailView.setRating(currPrd.getReviewRating());
            mDetailView.setNumOFReview(currPrd.getReviewCount());
        }
    }
}
