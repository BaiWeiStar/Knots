package cn.libery.knots.model.code;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Libery on 2016/8/9.
 * Email:libery.szq@qq.com
 */
public class ShaUrl implements Parcelable {
    private String sha;
    private String url;
    private String html_url;

    public static final Creator<ShaUrl> CREATOR = new Creator<ShaUrl>() {
        @Override
        public ShaUrl createFromParcel(Parcel in) {
            return new ShaUrl(in);
        }

        @Override
        public ShaUrl[] newArray(int size) {
            return new ShaUrl[size];
        }
    };

    public String getSha() {
        return sha;
    }

    public void setSha(final String sha) {
        this.sha = sha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(final String html_url) {
        this.html_url = html_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sha);
        dest.writeString(this.url);
        dest.writeString(this.html_url);
    }

    public ShaUrl() {
    }

    protected ShaUrl(Parcel in) {
        this.sha = in.readString();
        this.url = in.readString();
        this.html_url = in.readString();
    }

}
