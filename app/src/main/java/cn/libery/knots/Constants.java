package cn.libery.knots;

import android.os.Environment;

/**
 * Created by Libery on 2016/7/14.
 * Email:libery.szq@qq.com
 */
public class Constants {

    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Knots";
    public static final String FRAGMENT_TYPE = "fragment_type";
    public static final String FRAGMENT_TAG = "fragment_tag";
    public static final String FRAGMENT_RECENTLY = "fragment_recently";

    public static final String EXTRA_LOGIN_URL = "extra_login_url";
    public static final String EXTRA_REPO_OWNER = "extra_repo_owner";
    public static final String EXTRA_REPO_NAME = "extra_repo_name";
    public static final String EXTRA_REPO_SHA = "extra_repo_sha";
    public static final String EXTRA_REPO_FILE_NAME = "extra_repo_file_name";
    public static final String EXTRA_REPO_BRANCH = "extra_repo_branch";
    public static final String EXTRA_REPO_GIT_TREE = "extra_repo_git_tree";

    //SharePrefer
    public static final String SHARE_FIRST_START = "first_start";

    public static class GitHub {
        public static final String CLIENT_ID = "120bb0f512c576e8f7f0";
        public static final String SECRET = "20dc56d83f7303dc44fb91054918cba165dbea00";
        public static final String REDIRECT_URI = "http://libery.cn";
        public static final String SCOPE = "user,public_repo,repo,delete_repo,notifications,gist";
    }
}
