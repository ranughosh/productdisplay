package products.testproject.view;

import java.util.ArrayList;
import java.util.List;

import products.testproject.model.Product;

/**
 * Created by ghoshr on 11/6/2017.
 */

public interface MainView {
    public void showProgress();
    public void hideProgress();
    public void setErrorMessage(String errorMessage);
    public void showList();
    public void hideList();
    public void startDetailActivity(int position, List<Product> productList);
    public void updateScrollPosition();
}
