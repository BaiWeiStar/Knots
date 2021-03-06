package cn.libery.knots.api;


import android.text.TextUtils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.libery.knots.model.Result;
import cn.libery.knots.model.Token;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Libery on 2016/3/28.
 * Email:libery.szq@qq.com
 */
public class Api {

    public static final String BASE_DAILY_URL = "https://github.com/";

    private static final int DEFAULT_TIMEOUT = 15;

    private UserApiService apiService;

    //构造方法私有
    private Api() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);

        Interceptor interceptor = new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Headers headers = new Headers.Builder()
                        .add("Accept", "application/json")
                        .add("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .add("User-Agent", "Knots")
                        .build();
                Request request = chain.request()
                        .newBuilder()
                        .headers(headers)
                        .build();

                Response response = chain.proceed(request);
               /* int tryCount = 0;
                while (!response.isSuccessful() && tryCount < 3) {
                    response = chain.proceed(request);
                    tryCount++;
                    LogUtil.e("tryCount", tryCount + "");
                }*/
                return response;
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
        private static final Api INSTANCE = new Api();
    }

    //获取单例
    public static Api getInstance() {
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
            if (!TextUtils.isEmpty(t.error)) {
                throw new ApiException(t.error_description, t.error_uri);
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
    public void postObtainToken(Subscriber<Token> subscriber, String client_id, String client_secret, String code,
                                String redirect_uri) {
        Observable<Token> observable = apiService.postObtainToken(client_id, client_secret, code, redirect_uri).map
                (new HttpResultFunc<Token>());
        toSubscribe(observable, subscriber);
    }

}