package cn.libery.knots.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import cn.libery.knots.model.User;

/**
 * Created by Libery on 2016/7/22.
 * Email:libery.szq@qq.com
 */
@ModelContainer
@Table(database = KnotsDataBase.class)
public class UserRecord extends BaseModel {

    //自增ID
    @PrimaryKey(autoincrement = true)
    public Long _id;

    @Column
    public int id;

    @Column
    public String login;

    @Column
    public String avatar_url;

    @Column
    public String gravatar_id;

    @Column
    public String url;

    @Column
    public String html_url;

    @Column
    public String followers_url;

    @Column
    public String following_url;

    @Column
    public String gists_url;

    @Column
    public String starred_url;

    @Column
    public String subscriptions_url;

    @Column
    public String organizations_url;

    @Column
    public String repos_url;

    @Column
    public String events_url;

    @Column
    public String received_events_url;

    @Column
    public String type;

    @Column
    public boolean site_admin;

    @Column
    public String name;

    @Column
    public String company;

    @Column
    public String blog;

    @Column
    public String location;

    @Column
    public String email;

    @Column
    public String hireable;

    @Column
    public String bio;

    @Column
    public int public_repos;

    @Column
    public int public_gists;

    @Column
    public int followers;

    @Column
    public int following;

    @Column
    public String created_at;

    @Column
    public String updated_at;

    @Column
    public int private_gists;

    @Column
    public int total_private_repos;

    @Column
    public int owned_private_repos;

    @Column
    public int disk_usage;

    @Column
    public int collaborators;

    @Column
    public int starred;

    @Column
    public String accessToken;

    private static UserRecord setUser(UserRecord record, User user, boolean saveToken) {
        record.id = user.getId();
        record.login = user.getLogin();
        record.avatar_url = user.getAvatar_url();
        record.gravatar_id = user.getGravatar_id();
        record.url = user.getUrl();
        record.html_url = user.getHtml_url();
        record.followers_url = user.getFollowers_url();
        record.following_url = user.getFollowing_url();
        record.gists_url = user.getGists_url();
        record.starred_url = user.getStarred_url();
        record.subscriptions_url = user.getSubscriptions_url();
        record.organizations_url = user.getOrganizations_url();
        record.repos_url = user.getRepos_url();
        record.events_url = user.getEvents_url();
        record.received_events_url = user.getReceived_events_url();
        record.type = user.getType();
        record.site_admin = user.isSite_admin();
        record.name = user.getName();
        record.company = user.getCompany();
        record.blog = user.getBlog();
        record.location = user.getLocation();
        record.email = user.getEmail();
        record.hireable = user.getHireable();
        record.bio = user.getBio();
        record.public_repos = user.getPublic_repos();
        record.public_gists = user.getPublic_gists();
        record.followers = user.getFollowers();
        record.following = user.getFollowing();
        record.created_at = user.getCreated_at();
        record.updated_at = user.getUpdated_at();
        record.private_gists = user.getPrivate_gists();
        record.total_private_repos = user.getTotal_private_repos();
        record.owned_private_repos = user.getOwned_private_repos();
        record.disk_usage = user.getDisk_usage();
        record.collaborators = user.getCollaborators();
        if (saveToken) {
            record.accessToken = user.getAccessToken();
        }
        record.save();
        return record;
    }

    public static UserRecord saveUser(User user, boolean saveToken) {
        UserRecord record = getUserRecord();
        if (record != null) {
            record = setUser(record, user, saveToken);
        } else {
            record = new UserRecord();
            record = setUser(record, user, saveToken);
        }
        return record;
    }

    public static UserRecord getUserRecord() {
        return new Select().from(UserRecord.class).querySingle();
    }

    public static UserRecord updateStarred(User user) {
        UserRecord record = getUserRecord();
        record.starred = user.getStarred();
        record.update();
        return record;
    }

}
