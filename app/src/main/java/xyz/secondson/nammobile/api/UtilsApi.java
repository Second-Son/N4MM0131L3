package xyz.secondson.nammobile.api;

/**
 * Created by secondson on 17/11/17.
 */

public class UtilsApi {
    public static final String BASE_URL_API = "http://192.168.1.14:8080/";


    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
