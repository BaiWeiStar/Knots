package cn.libery.knots.utils;

import java.util.List;

/**
 * Created by Libery on 2016/8/2.
 * Email:libery.szq@qq.com
 */
public class CheckUtil {

    public static boolean isNotNullByList(List list) {
        return list != null && list.size() > 0;
    }

}
