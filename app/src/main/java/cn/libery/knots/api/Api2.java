package cn.libery.knots.api;

import android.text.TextUtils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.libery.knots.BuildConfig;
import cn.libery.knots.db.UserRecord;
import cn.libery.knots.model.Repository;
import cn.libery.knots.model.Result;
import cn.libery.knots.model.User;
import cn.libery.knots.utils.Logger;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Libery on 2016/7/22.
 * Email:libery.szq@qq.com
 */
public class Api2 {
    public static final String BASE_DAILY_URL = "https://api.github.com/";

    private static final int DEFAULT_TIMEOUT = 15;

    private UserApiService apiService;

    //构造方法私有
    private Api2() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);

        UserRecord record = UserRecord.getUserRecord();
        final String token;
        if (record != null) {
            token = "Authorization: token " + record.accessToken;
        } else {
            token = "";
        }
        Logger.e("api2" + token);
        Interceptor interceptor = new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Headers.Builder builder = new Headers.Builder()
                        .add("Accept", "application/json")
                        .add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .add("User-Agent", "Knots");
                if (!TextUtils.isEmpty(token)) {
                    builder.add(token);
                }
                Headers headers = builder.build();
                Request request = chain.request()
                        .newBuilder()
                        .headers(headers)
                        .build();

                return chain.proceed(request);
            }
        };

        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws
                    CertificateException {

            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws
                    CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        TrustManager[] trustAllCerts = new TrustManager[]{trustManager};
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            builder.sslSocketFactory(sc.getSocketFactory(), trustManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(final String hostname, final SSLSession session) {
                return true;
            }
        });

        builder.addInterceptor(interceptor);
        OkHttpClient client = builder.build();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder().addInterceptor(logging).build();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_DAILY_URL)
                .build();
        apiService = retrofit.create(UserApiService.class);

    }


    //在访问Api时创建单例
    private static class SingleApi {
        private static final Api2 INSTANCE = new Api2();
    }

    //获取单例
    public static Api2 getInstance() {
        return SingleApi.INSTANCE;
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 用来统一处理Http的result,并将HttpResult的业务数据部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型
     */
    private static class HttpResultFunc<T extends Result> implements Func1<T, T> {
        @Override
        public T call(final T t) {
            if (!TextUtils.isEmpty(t.message)) {
                throw new ApiException(t.message, t.documentation_url);
            } else {
                return t;
            }
        }
    }


    /**
     * 返回结果只有Result
     */
    private static class HttpResultFunc2 implements Func1<Result, Boolean> {

        @Override
        public Boolean call(final Result result) {
            if (TextUtils.isEmpty(result.error)) {
                return true;
            } else {
                throw new ApiException(result.error_description, result.error_uri);
            }
        }

    }

    //------------------------------------------------------------------------------------------------------------------

    public void authUserClient(Subscriber<User> subscriber, String access_token) {
        Observable<User> observable = apiService.authUserClient(access_token).map
                (new HttpResultFunc<User>());
        toSubscribe(observable, subscriber);
    }

    public void userProfile(Subscriber<User> subscriber, String userName) {
        Observable<User> observable = apiService.userProfile(userName).map
                (new HttpResultFunc<User>());
        toSubscribe(observable, subscriber);
    }

    public void getUserStarred(Subscriber<List<Repository>> subscriber, String userName, int page, int per_page) {
        Observable<List<Repository>> observable = apiService.getUserStarred(userName, page, per_page);
        toSubscribe(observable, subscriber);
    }
}
