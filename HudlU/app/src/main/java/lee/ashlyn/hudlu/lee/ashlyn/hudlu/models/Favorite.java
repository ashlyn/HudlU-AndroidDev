package lee.ashlyn.hudlu.lee.ashlyn.hudlu.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ashlyn.lee on 4/3/16.
 */
public class Favorite extends RealmObject {
    private String title;
    private String author;
    @PrimaryKey
    private String link;
    private String feature_image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeatureImage() {
        return feature_image;
    }

    public void setFeatureImage(String feature_image) {
        this.feature_image = feature_image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
