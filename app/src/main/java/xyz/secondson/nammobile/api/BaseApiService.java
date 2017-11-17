package xyz.secondson.nammobile.api;

import retrofit2.Call;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by secondson on 17/11/17.
 */

public interface BaseApiService {
    @FormUrlEncoded
    @POST("/api/nam/tambah")
    Call<ResponseBody> simpan(
            @Field("dnTotal") String dnTotal,
            @Field("caseTotal") String caseTotal,
            @Field("supplierCode") String supplierCode,
            @Field("dealerCode") String dealerCode);
//    Field merupakan parameter yang dibutuhkan API
}
