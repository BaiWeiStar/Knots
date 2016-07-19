package cn.libery.knots.api;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Libery on 2016/3/28.
 * Email:libery.szq@qq.com
 */
public interface ApiService {

    @GET("my_balance.php")
    Observable<Result> getBalance(@Query("shop_id") String shop_id, @Query("expo_id") String expo_id);

}
