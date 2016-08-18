package cn.libery.knots.model.code;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Libery on 2016/8/9.
 * Email:libery.szq@qq.com
 */
public class GitTree extends ShaUrl implements Parcelable {

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

    public boolean isBlob() {
        return "blob".equals(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.path);
        dest.writeString(this.mode);
        dest.writeString(this.type);
        dest.writeInt(this.size);
    }

    public GitTree() {
    }

    protected GitTree(Parcel in) {
        super(in);
        this.path = in.readString();
        this.mode = in.readString();
        this.type = in.readString();
        this.size = in.readInt();
    }

    public static final Creator<GitTree> CREATOR = new Creator<GitTree>() {
        @Override
        public GitTree createFromParcel(Parcel source) {
            return new GitTree(source);
        }

        @Override
        public GitTree[] newArray(int size) {
            return new GitTree[size];
        }
    };
}