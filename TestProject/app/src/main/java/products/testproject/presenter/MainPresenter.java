package products.testproject.presenter;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import products.testproject.adapter.RecyclerAdapter;
import products.testproject.constants.AppConstants;
import products.testproject.apicall.ApiUtils;
import products.testproject.apicall.ProductService;
import products.testproject.model.Product;
import products.testproject.model.ResponseObj;
import products.testproject.view.MainView;
import products.testproject.view.RecyclerListInterface;


/**
 * Created by ghoshr on 11/6/2017.
 */

public class MainPresenter {

    public MainPresenter(MainView mMainView) {
        this.mMainView = mMainView;
        mProductService= ApiUtils.getProductService();
    }

    private static String TAG="MainPresenter";
    private MainView mMainView;

    public List<Product> getProductList() {
        return mProductList;
    }

    private List<Product> mProductList;
    private RecyclerListInterface mRecyclerInterface;
    private ProductService mProductService;
    private Disposable disposable;
    private int totalItemCount;


    public int getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(int totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

    private Observer<ResponseObj> mObserver =new Observer<ResponseObj>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
          disposable=d;
        }

        @Override
        public void onComplete() {
            mMainView.hideProgress();
            Log.d(TAG, "on completed");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "error:" + e.toString());
            mMainView.hideProgress();
        }

        @Override
        public void onNext(ResponseObj productResponse) {

            if (productResponse != null) {
                if (productResponse.getStatus()!=200) {

                    if(!TextUtils.isEmpty(productResponse.getError()))
                    mMainView.setErrorMessage(productResponse.getError());

                } else {
                    if(productResponse.getProductList()!=null && productResponse.getProductList().size()>0) {
                        Log.d(TAG, "Response Array:" + productResponse.getProductList().size());
                        setProductList(productResponse.getProductList());

                    }
                     setTotalItemCount(productResponse.getTotalProducts());
                }
            }
        }
    };

    Observable<ResponseObj> mObservable;


    public void setListInterface(RecyclerListInterface listInterface) {
        mRecyclerInterface = listInterface;
    }


    public void setProductList(ArrayList<Product> productList){
        if(mProductList==null){
            mProductList=productList;
        }
        else{
            mProductList.addAll(productList);
        }
        showRecycler(mProductList);
        //mRecyclerInterface.setData(mProductList);
    }

    public void loadProduct(int pageNumber) {
           if(moreRecordExists(pageNumber)) {
               Log.d(TAG,"more record exists ");
               mMainView.showProgress();
               mObservable = mProductService.getProduct(getUrl(pageNumber));
               mObservable.subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread()).subscribe(mObserver);
           }
           else{
               Log.d(TAG,"more record does not exist");
           }

    }

    public String getUrl(int pageNumber){
        StringBuilder strUrl=new StringBuilder(AppConstants.endPointUrl);
        strUrl.append("walmartproducts/");
        strUrl.append(AppConstants.appId+"/");
        strUrl.append(pageNumber+"/");
        strUrl.append(AppConstants.pageSize);
        Log.d(TAG,"url:"+strUrl.toString());
        return strUrl.toString();
    }

    public void bindViewHolder(RecyclerAdapter.ProductViewHolder viewHolder, int position) {
        Log.d(TAG, "bind viewholder:" + mProductList.size());
        Product mProductObject = mProductList.get(position);
        viewHolder.setImage(mProductObject.getProductImage());
        viewHolder.setProductName(mProductObject.getProductName());
        viewHolder.setProductDesc(mProductObject.getProductShortDesc());
        viewHolder.setPrice(mProductObject.getPrice());
        viewHolder.setRating(mProductObject.getReviewRating());
        viewHolder.setInStock(mProductObject.isInStock());
        viewHolder.setNumOFReview(mProductObject.getReviewCount());
    }

    public void unsubsribe(){
       if(disposable!=null && !disposable.isDisposed())
           disposable.dispose();
    }

    public boolean moreRecordExists(int pageNumber){
        if((pageNumber-1)*AppConstants.pageSize<=totalItemCount)//index downgraded for the last page
            return true;
        else
            return false;
    }

    public void delegateOnClick(int aposition,int lposition){
        Log.d(TAG,"adapter position:"+aposition+","+lposition);
        mMainView.startDetailActivity(aposition,mProductList);
    }

    public void showRecycler(List<Product> data){
        mMainView.showList();
        mRecyclerInterface.setData(data);
    }

    public void updateScrollPosition(){
        mMainView.updateScrollPosition();
    }
}
