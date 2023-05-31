package th.o.earthquakereport;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://earthquake.usgs.gov/fdsnws/event/1/";
    private RecyclerView recyclerView;
    private EarthquakeAdapter adapter;
    Retrofit retrofit;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, setting.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutMain);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(3.0, 10.0, "time", null);
            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new EarthquakeAdapter();
        recyclerView.setAdapter(adapter);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        double minMagnitude = getIntent().getDoubleExtra("minMagnitudeCode", 3);
        double maxMagnitude = getIntent().getDoubleExtra("maxMagnitudeCode", 10);
        String order = getIntent().getStringExtra("orderCode");
        String alert = getIntent().getStringExtra("alertCode");

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutMain);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable()) {

                    getData(minMagnitude, maxMagnitude, order, alert);

                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(MainActivity.this, "Network Error !!!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        });


    }

    void getData(double minMagnitude, double maxMagnitude, String order, String alertLevel) {
        MyApiService apiService = retrofit.create(MyApiService.class);
        Call<EarthquakeResponse> call = apiService.getEarthquakes("geojson", minMagnitude, maxMagnitude, order, alertLevel);
        call.enqueue(new Callback<EarthquakeResponse>() {
            @Override
            public void onResponse(Call<EarthquakeResponse> call, Response<EarthquakeResponse> response) {
                if (response.isSuccessful()) {
                    EarthquakeResponse earthquakeResponse = response.body();
                    if (earthquakeResponse != null) {
                        List<Earthquake> earthquakes = earthquakeResponse.getEarthquakes();
                        // Retrieve the first 10 earthquakes or less if the list is smaller
                        List<Earthquake> displayedEarthquakes = earthquakes.subList(0, Math.min(100, earthquakes.size()));
                        adapter.setEarthquakes(displayedEarthquakes);

                    }
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<EarthquakeResponse> call, Throwable t) {
                // Handle network errors
            }
        });
    }

    interface MyApiService {
        @GET("query")
        Call<EarthquakeResponse> getEarthquakes(
                @Query("format") String format,
                @Query("minmagnitude") double minMagnitude,
                @Query(("maxmagnitude")) double maxMagnitude,
                @Query("orderby") String order,
                @Query("alertlevel") String alertLevel
        );
    }

    class EarthquakeResponse {
        @SerializedName("features")
        private List<Earthquake> earthquakes;

        public List<Earthquake> getEarthquakes() {
            return earthquakes;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}