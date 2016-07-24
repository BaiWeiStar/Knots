package cn.libery.knots.db;

import android.content.Context;

import cn.libery.knots.model.User;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Libery on 2016/7/22.
 * Email:libery.szq@qq.com
 */
public class UserRecord extends RealmObject {

    public int id;
    public String login;
    public String avatar_url;
    public String gravatar_id;
    public String url;
    public String html_url;
    public String followers_url;
    public String following_url;
    public String gists_url;
    public String starred_url;
    public String subscriptions_url;
    public String organizations_url;
    public String repos_url;
    public String events_url;
    public String received_events_url;
    public String type;
    public boolean site_admin;
    public String name;
    public String company;
    public String blog;
    public String location;
    public String email;
    public String hireable;
    public String bio;
    public int public_repos;
    public int public_gists;
    public int followers;
    public int following;
    public String created_at;
    public String updated_at;
    public int private_gists;
    public int total_private_repos;
    public int owned_private_repos;
    public int disk_usage;
    public int collaborators;
    public int starred;

    private static void setUser(UserRecord record, User user) {
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
        record.starred = user.getStarred();
    }

    public static void saveUser(Context context, User user) {
        UserRecord record = getUserRecord(context);
        Realm realm = RealmProvider.getInstance(context).getRealm();
        realm.beginTransaction();
        if (record != null) {
            setUser(record, user);
        } else {
            record = realm.createObject(UserRecord.class);
            setUser(record, user);
        }
        realm.commitTransaction();
        realm.close();
    }

    public static UserRecord getUserRecord(Context context) {
        Realm realm = RealmProvider.getInstance(context).getRealm();
        return realm.where(UserRecord.class).findFirst();
    }

}
