package cn.libery.knots.db;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Libery on 2016/7/23.
 * Email:libery.szq@qq.com
 */
public class RealmProvider {

    private Context mContext;
    private static RealmProvider mInstance;

    public RealmProvider(final Context context) {
        mContext = context.getApplicationContext();
    }

    public static RealmProvider getInstance(Context context) {
        context = context.getApplicationContext();
        if (mInstance == null) {
            synchronized (RealmProvider.class) {
                if (mInstance == null) {
                    mInstance = new RealmProvider(context);
                }
            }
        }
        return mInstance;
    }

    public Realm getRealm() {
        return Realm.getInstance(new RealmConfiguration.Builder(mContext).build());
    }
}
