package cn.libery.knots.model.code;

import java.util.Comparator;

/**
 * Created by Libery on 2016/8/11.
 * Email:libery.szq@qq.com
 */
public class TreeCompare implements Comparator<GitTree> {
    @Override
    public int compare(final GitTree lhs, final GitTree rhs) {
        if ("blob".equals(lhs.getType()) && "tree".equals(rhs.getType())) {
            return 1;
        } else if ("blob".equals(rhs.getType()) && "tree".equals(lhs.getType())) {
            return -1;
        } else {
            return 0;
        }
    }
}
