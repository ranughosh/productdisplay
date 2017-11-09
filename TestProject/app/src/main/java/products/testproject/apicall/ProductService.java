package products.testproject.apicall;

import io.reactivex.Observable;
import products.testproject.model.ResponseObj;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by ghoshr on 11/6/2017.
 */

public interface ProductService {
    @GET
    Observable<ResponseObj> getProduct(@Url String url);
}
