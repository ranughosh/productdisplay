package products.testproject.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import products.testproject.constants.AppConstants;
import products.testproject.R;
import products.testproject.model.Product;
import products.testproject.presenter.MainPresenter;
import products.testproject.view.RecyclerListInterface;
import products.testproject.view.RecyclerRowView;

/**
 * Created by ghoshr on 11/6/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProductViewHolder> implements RecyclerListInterface {

    private Context mContext;
    private MainPresenter mMainPresenter;
    private List<Product> mProductList;



    public RecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setMainPresenter(MainPresenter presenter) {
        mMainPresenter = presenter;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.item_row, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
       mMainPresenter.bindViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        if (mProductList == null)
            return 0;
        else
            return mProductList.size();
    }

    @Override
    public void setData(List<Product> productArray) {
        mProductList = productArray;
        notifyDataSetChanged();
        mMainPresenter.updateScrollPosition();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements RecyclerRowView,View.OnClickListener {

        ImageView mImageView;
        TextView mProductName;
        TextView mProductDesc;
        TextView mProductPrice;
        RatingBar mRating;
        TextView mIsInStock;
        TextView mNoOfReview;
        RelativeLayout mRatingWrapper;

        public ProductViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mImageView = (ImageView) view.findViewById(R.id.product_image);
            mProductName = (TextView) view.findViewById(R.id.product_name);
            mProductDesc = (TextView) view.findViewById(R.id.product_desc);
            mProductPrice = (TextView) view.findViewById(R.id.price);
            mRatingWrapper=(RelativeLayout) view.findViewById(R.id.rating_wrapper);
            mRating = (RatingBar) view.findViewById(R.id.product_rating);
            mNoOfReview=(TextView) view.findViewById(R.id.num_of_review);
            mIsInStock = (TextView) view.findViewById(R.id.instock);
        }

        @Override
        public void setProductName(String productName) {
          if(!TextUtils.isEmpty(productName))
            mProductName.setText(productName);
        }

        @Override
        public void setProductDesc(String productDesc) {
            if(!TextUtils.isEmpty(productDesc)) {
                mProductDesc.setText(Html.fromHtml(productDesc));
                if(mProductDesc.getVisibility()!=View.VISIBLE){
                    mProductDesc.setVisibility(View.VISIBLE);
                }
            }
            else
                mProductDesc.setVisibility(View.GONE);
        }

        @Override
        public void setPrice(String price) {
          if(!TextUtils.isEmpty(price))
            mProductPrice.setText(price);
        }

        @Override
        public void setRating(float rating) {
            if(rating>0.0f) {
                mRating.setRating(rating);
                if(mRatingWrapper.getVisibility()!=View.VISIBLE){
                    mRatingWrapper.setVisibility(View.VISIBLE);
                }
            }
            else{
                mRatingWrapper.setVisibility(View.GONE);
            }
        }

        @Override
        public void setInStock(boolean isInStock) {
            if(isInStock)
                mIsInStock.setVisibility(View.VISIBLE);
            else
                mIsInStock.setVisibility(View.GONE);
        }

        @Override
        public void setImage(String imageUrl) {
            if(!TextUtils.isEmpty(imageUrl))
            Picasso.with(mContext).load(imageUrl).into(mImageView);
        }

        @Override
        public void setNumOFReview(int noOfReview) {
            if(noOfReview>0){
                mNoOfReview.setText(mContext.getString(R.string.ratiingstr,String.valueOf(noOfReview)));
                if(mRatingWrapper.getVisibility()!=View.VISIBLE){
                    mRatingWrapper.setVisibility(View.VISIBLE);
                }
            }
            else{
                mRatingWrapper.setVisibility(View.GONE);
            }
        }

        //dont do anything only needed for detail
        @Override
        public void setLongDesc(String longDesc) {

        }

        @Override
        public void onClick(View v) {
            mMainPresenter.delegateOnClick(getAdapterPosition(),getLayoutPosition());
        }
    }

}
