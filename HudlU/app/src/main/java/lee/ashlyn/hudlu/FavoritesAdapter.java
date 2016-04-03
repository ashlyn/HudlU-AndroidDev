package lee.ashlyn.hudlu;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import lee.ashlyn.hudlu.lee.ashlyn.hudlu.models.Favorite;

/**
 * Created by ashlyn.lee on 3/8/16.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

    private List<Favorite> mListData;
    private OnAdapterInteractionListener mListener;
    private RequestQueue mRequestQueue;

    public FavoritesAdapter(Context context, List<Favorite> data) {
        mListener = (OnAdapterInteractionListener) context;
        mListData = data;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        FavoriteViewHolder vh = new FavoriteViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final FavoriteViewHolder holder, final int position) {
        Favorite item = mListData.get(position);
        holder.mTitleTextView.setText(item.getTitle());
        holder.mAuthorTextView.setText(item.getAuthor());

        ImageRequest request = new ImageRequest(item.getFeatureImage(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        holder.mImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Request Error", "Image did not load");
                    }
                });

        mRequestQueue.add(request);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClicked(v, position);
            }
        });

        holder.mFavoriteButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextView;
        TextView mAuthorTextView;
        ImageView mImageView;
        Button mFavoriteButton;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.item_title);
            mAuthorTextView = (TextView) itemView.findViewById(R.id.item_author);
            mImageView = (ImageView) itemView.findViewById(R.id.item_image);
            mFavoriteButton = (Button) itemView.findViewById(R.id.favorite_button);
        }
    }

    public interface OnAdapterInteractionListener {
        void onItemClicked(View view, int position);
    }
}
