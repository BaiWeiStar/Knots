package cn.libery.knots.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;

import cn.libery.knots.R;

/**
 * Created by Libery on 2016/7/23.
 * Email:libery.szq@qq.com
 */
public class SmartImageView extends ImageView {

    public SmartImageView(final Context context) {
        super(context);
    }

    public SmartImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImageUrl(String url) {
        setImageUrl(url, 0, 0, 0);
    }

    public void setImageUrl(String url, int radius) {
        setImageUrl(url, 0, 0, radius);
    }

    public void setImageUrl(String url, @DrawableRes int loadingDrawable, @DrawableRes int failDrawable, int radius) {
        if (loadingDrawable == 0) {
            loadingDrawable = R.mipmap.ic_launcher;
        }

        if (failDrawable == 0) {
            failDrawable = R.mipmap.ic_launcher;
        }
        Context context = getContext();
        if (context == null) {
            return;
        }
        DrawableTypeRequest<String> g = Glide.with(context).load(url);
        g.placeholder(loadingDrawable)
                .error(failDrawable)
                .fitCenter()
                .crossFade();
        if (radius != 0) {
            GlideRoundTransform transform = new GlideRoundTransform(context);
            transform.setRadius(radius);
            g.transform(transform);
        }
        g.into(this);
    }

}
