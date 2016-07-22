package cn.libery.knots.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.libery.knots.Constants;
import cn.libery.knots.R;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.webview_loading_pb)
    ProgressBar mWebPb;
    @BindView(R.id.web_content)
    WebView mWebContent;
    private String loginUrl = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void obtainParam(final Intent intent) {
        loginUrl = intent.getStringExtra(Constants.EXTRA_LOGIN_URL);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mWebContent.loadUrl(loginUrl);
        mWebContent.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (uri.getScheme().equals("http")) {
                    Intent data = new Intent();
                    data.setData(uri);
                    setResult(RESULT_OK, data);
                    finish();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mWebContent.setWebChromeClient(new MyWebChromeClient());
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress < 100 && mWebPb.getVisibility() == ProgressBar.INVISIBLE) {
                mWebPb.setVisibility(ProgressBar.VISIBLE);
            }
            mWebPb.setProgress(newProgress);
            if (newProgress == 100) {
                mWebPb.setVisibility(ProgressBar.INVISIBLE);
            }
        }
    }

}
