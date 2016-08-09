package cn.libery.knots.model.code;

/**
 * Created by Libery on 2016/8/9.
 * Email:libery.szq@qq.com
 */
public class Blob extends ShaUrl {
    /**
     * sha : dca8d4cea8b107cb6faa7ec4c64a9a72efdcc3e6
     * size : 36
     * url : https://api.github.com/repos/Thewhitelight/Knots/git/blobs/dca8d4cea8b107cb6faa7ec4c64a9a72efdcc3e6
     * content : IyBLbm90cwpHaXRIdWIgU3RhcnJlZCBUYWcgIEFuZHJvaWQK
     * encoding : base64
     */

    private int size;
    private String content;
    private String encoding;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
