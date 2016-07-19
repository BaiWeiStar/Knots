package cn.libery.knots.api.subscribers;


import cn.libery.knots.utils.AppUtil;
import cn.libery.knots.utils.Logger;
import rx.Subscriber;

/**
 * Created by Libery on 2016/3/29.
 * Email:libery.szq@qq.com
 */
public class MySubscriber<T> extends Subscriber<T> {

    private SubscriberListener<T> mSubscriberListener;


    public MySubscriber(final SubscriberListener<T> subscriberListener) {
        mSubscriberListener = subscriberListener;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(final Throwable e) {
        Logger.e(e);
        AppUtil.universalException(e);
        if (mSubscriberListener != null) {
            mSubscriberListener.onError(e);
        }
    }

    @Override
    public void onNext(final T t) {
        if (mSubscriberListener != null) {
            mSubscriberListener.onNext(t);
        }
    }

}
