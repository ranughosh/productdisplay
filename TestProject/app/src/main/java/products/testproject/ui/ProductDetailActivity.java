package products.testproject.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import products.testproject.R;
import products.testproject.constants.AppConstants;
import products.testproject.customview.ActivitySwipeDetector;
import products.testproject.model.Product;
import products.testproject.presenter.DetailPresenter;
import products.testproject.view.RecyclerRowView;
/**
 * Created by ghoshr on 11/7/2017.
 */

public class ProductDetailActivity extends AppCompatActivity implements RecyclerRowView{


    private ImageView mImageView;
    private TextView mProductName;
    private TextView mProductDesc;
    private TextView mProductPrice;
    private RatingBar mRating;
    private TextView mIsInStock;
    private TextView mNoOfReview;
    private RelativeLayout mRatingWrapper;
    private ArrayList<Product> mProductList;
    private DetailPresenter mDetailPresenter;
    private int mPosition = 0;

    private static String TAG="ProductDetailActivity";
    private GestureDetector mGestureDetector;

    @Override
    public void setProductName(String productName) {
        if (!TextUtils.isEmpty(productName))
            mProductName.setText(productName);
    }

    @Override
    public void setProductDesc(String productDesc) {

    }

    @Override
    public void setPrice(String price) {
        if (!TextUtils.isEmpty(price))
            mProductPrice.setText(price);
    }

    @Override
    public void setRating(float rating) {
        if (rating > 0.0f) {
            mRating.setRating(rating);
            if (mRatingWrapper.getVisibility() != View.VISIBLE) {
                mRatingWrapper.setVisibility(View.VISIBLE);
            }
        } else {
            mRatingWrapper.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNumOFReview(int noOfReview) {
        if (noOfReview > 0) {
            mNoOfReview.setText(getString(R.string.ratiingstr, String.valueOf(noOfReview)));
            if (mRatingWrapper.getVisibility() != View.VISIBLE) {
                mRatingWrapper.setVisibility(View.VISIBLE);
            }
        } else {
            mRatingWrapper.setVisibility(View.GONE);
        }
    }

    @Override
    public void setInStock(boolean isInStock) {
        if (isInStock)
            mIsInStock.setVisibility(View.VISIBLE);
        else
            mIsInStock.setVisibility(View.GONE);
    }

    @Override
    public void setImage(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl))
            Picasso.with(this).load(imageUrl).into(mImageView);
    }

    @Override
    public void setLongDesc(String longDesc) {
        if (!TextUtils.isEmpty(longDesc))
            mProductDesc.setText(Html.fromHtml(longDesc));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        initUI();
    }

    private void initUI() {
        mImageView = (ImageView) findViewById(R.id.product_image);
        mProductName = (TextView) findViewById(R.id.product_name);
        mProductDesc = (TextView) findViewById(R.id.product_long_desc);
        mProductPrice = (TextView) findViewById(R.id.product_price);
        mRatingWrapper = (RelativeLayout) findViewById(R.id.rating_wrapper);
        mRating = (RatingBar) findViewById(R.id.product_rating);
        mNoOfReview = (TextView) findViewById(R.id.num_of_review);
        mIsInStock = (TextView) findViewById(R.id.instock);
        if (getIntent() != null && getIntent().hasExtra(AppConstants.productDetailArray)) {
            mProductList = getIntent().getParcelableArrayListExtra(AppConstants.productDetailArray);
        }
        mDetailPresenter = new DetailPresenter(this);
        mDetailPresenter.setProductList(mProductList);

        ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
        View parentView=findViewById(R.id.product_detail);
        parentView.setOnTouchListener(activitySwipeDetector);
        updateUI(mPosition);
    }

    public void updateUI(int position) {
        mDetailPresenter.updateUI(position);
    }

    public void changeUI(boolean isForward){
        if(isForward){
            mPosition = mPosition +1;
            updateUI(mPosition);
        }
        else {
            if(mPosition >0) {
                mPosition = mPosition -1;
                updateUI(mPosition);
            }
        }
    }
}
