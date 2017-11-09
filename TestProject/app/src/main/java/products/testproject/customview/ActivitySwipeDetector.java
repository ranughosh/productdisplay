package products.testproject.customview;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import products.testproject.ui.ProductDetailActivity;

/**
 * Created by ghoshr on 11/8/2017.
 */

public class ActivitySwipeDetector implements View.OnTouchListener {

    static final String logTag = "ActivitySwipeDetector";
    private ProductDetailActivity activity;
    static final int MIN_DISTANCE = 50;
    private float downX, downY, upX, upY;

    public ActivitySwipeDetector(ProductDetailActivity activity){
        this.activity = activity;
    }

    public void onRightSwipe(){
        Log.i(logTag, "RightToLeftSwipe!");
        activity.changeUI(false);
    }

    public void onLeftSwipe(){
        Log.i(logTag, "LeftToRightSwipe!");
        activity.changeUI(true);
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // swipe horizontal?
                if(Math.abs(deltaX) > Math.abs(deltaY))
                {
                    if(Math.abs(deltaX) > MIN_DISTANCE){
                        // left or right
                        if(deltaX > 0) { this.onRightSwipe(); return true; }
                        if(deltaX < 0) { this.onLeftSwipe(); return true; }
                    }
                    else {
                        Log.i(logTag, "Horizontal Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE);
                        return false; // We don't consume the event
                    }
                }


                return true;
            }
        }
        return false;
    }

}
