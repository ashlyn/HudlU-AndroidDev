package lee.ashlyn.hudlu;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import lee.ashlyn.hudlu.lee.ashlyn.hudlu.models.*;

/**
 * Created by ashlyn.lee on 4/3/16.
 */
public class FavoriteUtil {
    public static void addFavorite(Context context, MashableNewsItem newsItem) {
        Favorite favorite = new Favorite();
        favorite.setTitle(newsItem.title);
        favorite.setAuthor(newsItem.author);
        favorite.setLink(newsItem.link);
        favorite.setFeatureImage(newsItem.feature_image);

        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.copyToRealm(favorite);
        realm.commitTransaction();
    }

    public static void removeFavorite(Context context, MashableNewsItem newsItem) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.where(Favorite.class).equalTo("link", newsItem.link).findFirst().removeFromRealm();
        realm.commitTransaction();
    }

    public static boolean isFavorite(Context context, MashableNewsItem newsItem) {
        Realm realm = Realm.getInstance(context);
        return realm.where(Favorite.class).equalTo("link", newsItem.link).count() > 0;
    }

    public static RealmResults<Favorite> getAllFavorites(Context context) {
        Realm realm = Realm.getInstance(context);
        return realm.where(Favorite.class).findAll();
    }

}
