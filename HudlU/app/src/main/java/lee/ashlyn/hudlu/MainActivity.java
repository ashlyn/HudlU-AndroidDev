package lee.ashlyn.hudlu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import lee.ashlyn.hudlu.lee.ashlyn.hudlu.models.MashableNews;
import lee.ashlyn.hudlu.lee.ashlyn.hudlu.models.MashableNewsItem;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnAdapterInteractionListener {
    static SharedPreferences preferences;
    static SharedPreferences.Editor editor;
    static final String PREFERENCES_NAME = "HudlUPreferences";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private final List<MashableNewsItem> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        showWelcomeAlert();

        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        // use LinearLayoutManager for RecyclerView
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // use MyAdapter for RecyclerView adapter
        MyAdapter adapter = new MyAdapter(this, mData);
        mRecyclerView.setAdapter(adapter);

        // get latest news
        fetchLatestNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(View view, int position) {
        if (view.getId() == R.id.favorite_button) {
            MashableNewsItem item = mData.get(position);
            boolean isFavorite = FavoriteUtil.isFavorite(this, item);
            if (!isFavorite) {
                FavoriteUtil.addFavorite(this, item);
            } else {
                FavoriteUtil.removeFavorite(this, item);
            }

            mRecyclerView.getAdapter().notifyDataSetChanged();
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mData.get(position).link));
            startActivity(intent);
        }
    }

    public void fetchLatestNews() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            Toast.makeText(this, getString(R.string.fetching_news), Toast.LENGTH_SHORT).show();

            StringRequest request = new StringRequest(Request.Method.GET,
                    "http://mashable.com/stories.json?hot_per_page=0&new_per_page=5&rising_per_page=0",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            MashableNews news = new Gson().fromJson(response, MashableNews.class);
                            Log.d("Fetched News", news.newsItems.get(0).title);
                            mData.addAll(news.newsItems);
                            mRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Context context = getApplicationContext();
                            Toast.makeText(context, getString(R.string.generic_error), Toast.LENGTH_SHORT).show();
                        }
                    });

            requestQueue.add(request);
        } else {
            Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void showWelcomeAlert() {
        boolean welcomeShown = preferences.getBoolean("WelcomeShown", false);

        if (!welcomeShown) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.welcome_message)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editor.putBoolean("WelcomeShown", true).commit();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
