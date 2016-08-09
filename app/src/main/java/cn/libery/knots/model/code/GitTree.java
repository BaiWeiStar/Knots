package cn.libery.knots.model.code;

/**
 * Created by Libery on 2016/8/9.
 * Email:libery.szq@qq.com
 */
public class GitTree extends ShaUrl {

    private String path;
    private String mode;
    private String type;
    private int size;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}