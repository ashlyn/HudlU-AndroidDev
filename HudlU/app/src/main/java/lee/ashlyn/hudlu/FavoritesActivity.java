package lee.ashlyn.hudlu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;
import lee.ashlyn.hudlu.lee.ashlyn.hudlu.models.Favorite;

public class FavoritesActivity extends AppCompatActivity implements FavoritesAdapter.OnAdapterInteractionListener {
    private RecyclerView mRecylerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private final List<Favorite> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecylerView = (RecyclerView) findViewById(R.id.favorites_list);

        // use LinearLayoutManager for favorites RecylerView
        mLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(mLayoutManager);

        // use FavoritesAdapter for RecylerView adapter
        FavoritesAdapter adapter = new FavoritesAdapter(this, mData);
        mRecylerView.setAdapter(adapter);

        // Get favorites from Realm
        mData.addAll(FavoriteUtil.getAllFavorites(this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(View view, int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mData.get(position).getLink()));
        startActivity(intent);
    }
}
