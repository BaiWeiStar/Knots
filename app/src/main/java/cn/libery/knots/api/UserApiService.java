package cn.libery.knots.api;

import java.util.List;

import cn.libery.knots.model.Repository;
import cn.libery.knots.model.Token;
import cn.libery.knots.model.User;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Libery on 2016/3/28.
 * Email:libery.szq@qq.com
 */
public interface UserApiService {

    @FormUrlEncoded
    @POST("login/oauth/access_token")
    Observable<Token> postObtainToken(@Field("client_id") String client_id,
                                      @Field("client_secret") String client_secret,
                                      @Field("code") String code,
                                      @Field("redirect_uri") String redirect_uri);


    @GET("user")
    Observable<User> authUserClient(@Query("access_token") String access_token);

    @GET("users/{name}")
    Observable<User> userProfile(@Path("name") String userName);

    @Headers("Cache-Control: public, max-age=60")
    @GET("users/{name}/starred")
    Observable<List<Repository>> getUserStarred(@Path("name") String userName,
                                                @Query("page") int page,
                                                @Query("per_page") int per_page);
}
