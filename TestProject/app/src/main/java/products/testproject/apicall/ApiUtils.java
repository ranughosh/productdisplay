package products.testproject.apicall;

import products.testproject.constants.AppConstants;

/**
 * Created by ghoshr on 11/6/2017.
 */

public class ApiUtils {
    public static ProductService getProductService(){
        return RetrofitClient.getClient(AppConstants.endPointUrl).create(ProductService.class);
    }
}
