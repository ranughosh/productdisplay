package products.testproject.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import products.testproject.adapter.RecyclerAdapter;
import products.testproject.constants.AppConstants;
import products.testproject.R;
import products.testproject.model.Product;
import products.testproject.presenter.MainPresenter;
import products.testproject.customview.EndlessScrollListener;
import products.testproject.view.MainView;

import static products.testproject.constants.AppConstants.DATA;
import static products.testproject.constants.AppConstants.KEY_RECYCLER_STATE;

public class MainActivity extends AppCompatActivity implements MainView{

    private MainPresenter mMainPresenter;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecycleAdapter;
    private EndlessScrollListener mScrollListener;
    private static String TAG="MainActivity";
    private ProgressBar mProgressBar;
    private TextView mErrorText;
    private static Bundle mBundleRecyclerViewState;


    @Override
    public void showProgress() {
      mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        mErrorText.setText(errorMessage);
        mErrorText.setVisibility(View.VISIBLE);
        hideList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadData();
    }

    private void initUI(){
        mRecyclerView=(RecyclerView) findViewById(R.id.product_list);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMainPresenter=new MainPresenter(this);
        mRecycleAdapter=new RecyclerAdapter(this);
        mRecyclerView.setAdapter(mRecycleAdapter);
        mRecycleAdapter.setMainPresenter(mMainPresenter);
        mMainPresenter.setListInterface(mRecycleAdapter);
        mProgressBar=(ProgressBar) findViewById(R.id.progress_indicator);
        mErrorText=(TextView) findViewById(R.id.error_message);

        mScrollListener =new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page) {
               Log.d(TAG,"page:"+page);
                mMainPresenter.loadProduct(page);
            }
        };
        mRecyclerView.addOnScrollListener(mScrollListener);

    }

    private void initialLoad(){
        AppConstants.pageNumber = 1;
        mMainPresenter.loadProduct(AppConstants.pageNumber);
    }

    @Override
    public void startDetailActivity(int position, List<Product> productList) {
        if(productList.size()>position){
            Intent i=new Intent(this,ProductDetailActivity.class);
            ArrayList<Product> detailArray=new ArrayList<Product>();
            detailArray.addAll(productList.subList(position,productList.size()));
            i.putExtra(AppConstants.productDetailArray,detailArray);
            startActivityForResult(i,1);
        }
    }

    private void loadData(){
        if (mBundleRecyclerViewState != null) {
            int scrolledPosition = mBundleRecyclerViewState.getInt(KEY_RECYCLER_STATE);
            Log.d(TAG,"scrolled position recovered:"+scrolledPosition);
            ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(scrolledPosition,0);
            if(mBundleRecyclerViewState.getParcelableArrayList(DATA)!=null){

                ArrayList<Product> prdList=mBundleRecyclerViewState.getParcelableArrayList(DATA);
                if(prdList!=null && prdList.size()>0) {
                    mMainPresenter.setProductList(prdList);
                    mMainPresenter.showRecycler(prdList);
                }
            }
            else{
                initialLoad();
            }
        }
        else {
            if (mMainPresenter != null) {
                if (mMainPresenter.getProductList() != null) {
                    mMainPresenter.showRecycler(mMainPresenter.getProductList());

                } else {

                    initialLoad();
                }
            } else {
                initUI();
            }
        }

    }

    @Override
    public void showList() {
        if(mRecyclerView.getVisibility()==View.GONE){
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideList() {
        if(mRecyclerView.getVisibility()==View.VISIBLE){
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        int lastFirstVisiblePosition = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        Log.d(TAG,"scrolledPosition stored:"+lastFirstVisiblePosition);
        mBundleRecyclerViewState.putInt(KEY_RECYCLER_STATE,lastFirstVisiblePosition);
        if(mMainPresenter!=null && mMainPresenter.getProductList()!=null) {
            ArrayList<Product> storedProduct = new ArrayList(mMainPresenter.getProductList());
            mBundleRecyclerViewState.putParcelableArrayList(DATA, storedProduct);
        }
    }

    @Override
    public void updateScrollPosition() {
        if (mBundleRecyclerViewState != null) {
            int scrolledPosition = mBundleRecyclerViewState.getInt(KEY_RECYCLER_STATE,0);
            ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(scrolledPosition,0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.unsubsribe();//for orientation changes
    }

}
