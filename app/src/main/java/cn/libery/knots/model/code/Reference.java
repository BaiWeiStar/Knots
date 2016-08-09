package cn.libery.knots.model.code;

import cn.libery.knots.model.Result;

/**
 * Created by Libery on 2016/8/9.
 * Email:libery.szq@qq.com
 */
public class Reference extends Result {
    /**
     * ref : refs/heads/develop
     * url : https://api.github.com/repos/Thewhitelight/Knots/git/refs/heads/develop
     * object : {"sha":"cd562d6e97f3feeed06f8acfc91f142b73fbd53e","type":"commit","url":"https://api.github
     * .com/repos/Thewhitelight/Knots/git/commits/cd562d6e97f3feeed06f8acfc91f142b73fbd53e"}
     */

    private String ref;
    private String url;
    /**
     * sha : cd562d6e97f3feeed06f8acfc91f142b73fbd53e
     * type : commit
     * url : https://api.github.com/repos/Thewhitelight/Knots/git/commits/cd562d6e97f3feeed06f8acfc91f142b73fbd53e
     */

    private ObjectBean object;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean {
        private String sha;
        private String type;
        private String url;

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
