package products.testproject.view;

/**
 * Created by ghoshr on 11/6/2017.
 */

public interface RecyclerRowView {
    public void setProductName(String productName);
    public void setProductDesc(String productDesc);
    public void setPrice(String price);
    public void setRating(float rating);
    public void setNumOFReview(int noOfReview);
    public void setInStock(boolean isInStock);
    public void setImage(String imageUrl);
    public void setLongDesc(String longDesc);
   // public void setOnClick(int position);
}
