package cn.libery.knots.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Libery on 2016/7/24.
 * Email:libery.szq@qq.com
 */
@Database(name = KnotsDataBase.NAME, version = KnotsDataBase.VERSION)
public class KnotsDataBase {

    public static final String NAME = "Knots";

    public static final int VERSION = 1;

}
