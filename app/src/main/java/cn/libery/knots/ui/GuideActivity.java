package cn.libery.knots.ui;

import android.content.Intent;
import android.net.Uri;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.libery.knots.Constants;
import cn.libery.knots.R;
import cn.libery.knots.api.Api;
import cn.libery.knots.api.Api2;
import cn.libery.knots.api.subscribers.ProgressSubscriber;
import cn.libery.knots.api.subscribers.SubscriberListener;
import cn.libery.knots.db.UserRecord;
import cn.libery.knots.model.Token;
import cn.libery.knots.model.User;
import cn.libery.knots.utils.Logger;
import cn.libery.knots.utils.SharedPreferUtil;
import okhttp3.HttpUrl;

public class GuideActivity extends BaseActivity {

    public static final String OAUTH_HOST = "www.github.com";
    public static final int WEBVIEW_REQUEST_CODE = 1;

    @BindView(R.id.github_login)
    Button mGithubLogin;

    @Override
    protected int getContentView() {
        return R.layout.activity_guide;
    }

    @Override
    protected void obtainParam(final Intent intent) {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.github_login)
    public void onClick() {
        openLoginInBrowser();
    }

    private void openLoginInBrowser() {
        HttpUrl.Builder url = new HttpUrl.Builder()
                .scheme("https")
                .host(OAUTH_HOST)
                .addPathSegment("login")
                .addPathSegment("oauth")
                .addPathSegment("authorize")
                .addQueryParameter("client_id", Constants.GitHub.CLIENT_ID)
                .addQueryParameter("scope", Constants.GitHub.SCOPE);

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(Constants.EXTRA_LOGIN_URL, url.toString());
        startActivityForResult(intent, WEBVIEW_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WEBVIEW_REQUEST_CODE && resultCode == RESULT_OK)
            onUserLoggedIn(data.getData());
    }


    private void onUserLoggedIn(Uri uri) {
        if (uri != null && uri.getScheme().equals("http")) {
            String code = uri.getQueryParameter("code");
            ProgressSubscriber<Token> subscriber = new ProgressSubscriber<>(this, new SubscriberListener<Token>() {
                @Override
                public void onNext(final Token token) {
                    if (token != null) {
                        endAuth(token.access_token, Constants.GitHub.SCOPE);
                    }
                }

                @Override
                public void onError(final Throwable e) {

                }
            });
            Api.getInstance().postObtainToken(subscriber, Constants.GitHub.CLIENT_ID, Constants.GitHub.SECRET, code,
                    Constants.GitHub.REDIRECT_URI);
            mSubscription.add(subscriber);
        }
    }

    private void endAuth(final String access_token, final String scope) {
        Logger.e(access_token);
        ProgressSubscriber<User> subscriber = new ProgressSubscriber<>(this, new SubscriberListener<User>() {
            @Override
            public void onNext(final User user) {
                if (user != null) {
                    user.setAccessToken(access_token);
                    UserRecord.saveUser(user, true);
                    SharedPreferUtil.put(Constants.SHARE_FIRST_START, true);
                    startMainActivity();
                }
            }

            @Override
            public void onError(final Throwable e) {

            }
        });
        Api2.getInstance().authUserClient(subscriber, access_token);
        mSubscription.add(subscriber);
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
