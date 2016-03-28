package lee.ashlyn.hudlu.lee.ashlyn.hudlu.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ashlyn.lee on 3/28/16.
 */
public class MashableNews {
    // the Mashable API returns a list of news items called 'new' in JSON
    // it also lists 'rising', 'hot', and 'channel' items, but we don't want those
    @SerializedName("new")
    public List<MashableNewsItem> newsItems;
}
