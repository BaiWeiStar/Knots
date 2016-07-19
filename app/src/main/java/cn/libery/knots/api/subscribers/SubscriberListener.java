package cn.libery.knots.api.subscribers;

public interface SubscriberListener<T> {
    void onNext(T t);

    void onError(Throwable e);
}
