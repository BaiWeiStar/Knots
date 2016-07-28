package cn.libery.knots.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import cn.libery.knots.utils.PixelUtil;

/**
 * Created by Libery on 2016/7/28.
 * Email:libery.szq@qq.com
 */
public class GlideRoundTransform extends BitmapTransformation {

    private float radius = 0f;

    public GlideRoundTransform(final BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    public GlideRoundTransform(final Context context) {
        super(context);
    }

    public GlideRoundTransform(final Context context, int dp) {
        super(context);
        radius = PixelUtil.dp2px(dp);
    }

    @Override
    protected Bitmap transform(final BitmapPool pool, final Bitmap toTransform, final int outWidth, final int
            outHeight) {
        return roundCrop(pool, toTransform);
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.RGB_565);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.RGB_565);
        }

        Canvas canvas = new Canvas();
        canvas.setBitmap(result);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        canvas.drawColor(Color.WHITE);
        return result;
    }


}
