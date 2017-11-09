package products.testproject.customview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import products.testproject.constants.AppConstants;

/**
 * Created by ghoshr on 11/7/2017.
 */

public abstract  class EndlessScrollListener extends RecyclerView.OnScrollListener {
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;
    private static String TAG="EndlessScrollListener";

    @Override
    public void onScrolled(RecyclerView mRecyclerView, int dx, int dy)
    {
        super.onScrolled(mRecyclerView, dx, dy);
        LinearLayoutManager mLayoutManager = (LinearLayoutManager) mRecyclerView
                .getLayoutManager();

        visibleItemCount = mRecyclerView.getChildCount();
        totalItemCount = mLayoutManager.getItemCount();
        firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
        onScroll(firstVisibleItem, visibleItemCount, totalItemCount);
    }

    public void onScroll(int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        //Log.d(TAG,"first visible:"+firstVisibleItem+",visibleItemCount:"+visibleItemCount+",totalItemCount:"+totalItemCount+","+previousTotalItemCount);
        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount)
        {

            AppConstants.pageNumber=1;//initialize again
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0)
            {
                this.loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount))
        {
            loading = false;
            previousTotalItemCount = totalItemCount;
            //AppConstants.pageNumber++;
        }

        //Log.d(TAG,"loading:"+loading);
        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem +
                visibleThreshold))
        {
            AppConstants.pageNumber++;
            Log.d(TAG,"pageNumber:"+AppConstants.pageNumber);
            onLoadMore(AppConstants.pageNumber);
            loading = true;
        }
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page);

}