package cn.libery.knots.model.code;

import java.util.List;

/**
 * Created by Libery on 2016/8/9.
 * Email:libery.szq@qq.com
 */
public class Tree extends ShaUrl {
    /**
     * sha : cd562d6e97f3feeed06f8acfc91f142b73fbd53e
     * url : https://api.github.com/repos/Thewhitelight/Knots/git/trees/cd562d6e97f3feeed06f8acfc91f142b73fbd53e
     * tree : [{"path":".gitignore","mode":"100644","type":"blob","sha":"907c0e906af248c360f124ef0e637e740eb4c95c",
     * "size":67,"url":"https://api.github
     * .com/repos/Thewhitelight/Knots/git/blobs/907c0e906af248c360f124ef0e637e740eb4c95c"},{"path":"LICENSE",
     * "mode":"100644","type":"blob","sha":"8dada3edaf50dbc082c9a125058f25def75e625a","size":11357,"url":"https://api
     * .github.com/repos/Thewhitelight/Knots/git/blobs/8dada3edaf50dbc082c9a125058f25def75e625a"},{"path":"README
     * .md","mode":"100644","type":"blob","sha":"dca8d4cea8b107cb6faa7ec4c64a9a72efdcc3e6","size":36,
     * "url":"https://api.github.com/repos/Thewhitelight/Knots/git/blobs/dca8d4cea8b107cb6faa7ec4c64a9a72efdcc3e6"},
     * {"path":"app","mode":"040000","type":"tree","sha":"b911b0965c846059ca1abcc653fa129211f11c04",
     * "url":"https://api.github.com/repos/Thewhitelight/Knots/git/trees/b911b0965c846059ca1abcc653fa129211f11c04"},
     * {"path":"build.gradle","mode":"100644","type":"blob","sha":"165abe55184b1d14bb667af09aec6c340fff339e",
     * "size":565,"url":"https://api.github
     * .com/repos/Thewhitelight/Knots/git/blobs/165abe55184b1d14bb667af09aec6c340fff339e"},{"path":"gradle
     * .properties","mode":"100644","type":"blob","sha":"6fba363b404f7b602101d9aa5d3deb40be1ea906","size":991,
     * "url":"https://api.github.com/repos/Thewhitelight/Knots/git/blobs/6fba363b404f7b602101d9aa5d3deb40be1ea906"},
     * {"path":"gradle","mode":"040000","type":"tree","sha":"eff07ea2def1482e259588e27d1c398bd6b4c8c2",
     * "url":"https://api.github.com/repos/Thewhitelight/Knots/git/trees/eff07ea2def1482e259588e27d1c398bd6b4c8c2"},
     * {"path":"gradlew","mode":"100644","type":"blob","sha":"9d82f78915133e1c35a6ea51252590fb38efac2f","size":4971,
     * "url":"https://api.github.com/repos/Thewhitelight/Knots/git/blobs/9d82f78915133e1c35a6ea51252590fb38efac2f"},
     * {"path":"gradlew.bat","mode":"100644","type":"blob","sha":"8a0b282aa6885fb573c106b3551f7275c5f17e8e",
     * "size":2314,"url":"https://api.github
     * .com/repos/Thewhitelight/Knots/git/blobs/8a0b282aa6885fb573c106b3551f7275c5f17e8e"},{"path":"settings.gradle",
     * "mode":"100644","type":"blob","sha":"e7b4def49cb53d9aa04228dd3edb14c9e635e003","size":15,"url":"https://api
     * .github.com/repos/Thewhitelight/Knots/git/blobs/e7b4def49cb53d9aa04228dd3edb14c9e635e003"}]
     * truncated : false
     */
    private boolean truncated;
    /**
     * path : .gitignore
     * mode : 100644
     * type : blob
     * sha : 907c0e906af248c360f124ef0e637e740eb4c95c
     * size : 67
     * url : https://api.github.com/repos/Thewhitelight/Knots/git/blobs/907c0e906af248c360f124ef0e637e740eb4c95c
     */

    private List<GitTree> tree;

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public List<GitTree> getTree() {
        return tree;
    }

    public void setTree(List<GitTree> tree) {
        this.tree = tree;
    }

}
