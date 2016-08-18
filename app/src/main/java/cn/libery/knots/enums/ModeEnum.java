package cn.libery.knots.enums;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Libery on 2016/8/18.
 * Email:libery.szq@qq.com
 */
public class ModeEnum {
    public static final String TREE = "tree";
    public static final String BLOB = "blob";

    @StringDef({TREE, BLOB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RepoMode {
    }
}
