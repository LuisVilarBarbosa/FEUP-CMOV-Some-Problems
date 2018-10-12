package pt.up.fe.up201405729.lunchlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    public final static String ID_EXTRA = "RestaurantPosition";
    private LunchApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setIcon(R.drawable.rest_icon);
            bar.setDisplayShowHomeEnabled(true);
        }

        ListView list = findViewById(R.id.listView);
        app = (LunchApp) getApplicationContext();
        app.adapter = new RestaurantAdapter(this, android.R.layout.simple_list_item_1, app.restaurants);
        list.setAdapter(app.adapter);
        list.setEmptyView(findViewById(R.id.empty_list));
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, DetailsActivity.class);
        app.current = app.restaurants.get(position);
        i.putExtra(ID_EXTRA, position); // pass the position to the Details activity
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toast) {
            String message = "No restaurant selected";
            if (app.current != null)
                message = String.format("%s:\n%s", app.current.getName(), app.current.getNotes());
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return (true);
        } else if (item.getItemId() == R.id.add) {
            startActivity(new Intent(this, DetailsActivity.class));
            return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    class RestaurantAdapter extends ArrayAdapter<Restaurant> {

        public RestaurantAdapter(Context context, int resourceId, List<Restaurant> objects) {
            super(context, resourceId, objects); // the Activity, row layout, and array of values
        }

        @Override
        public @NonNull
        View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.row, parent, false); // get our custom layout
            }
            Restaurant r = app.restaurants.get(position);
            ((TextView) row.findViewById(R.id.title)).setText(r.getName()); // fill restaurant name
            ((TextView) row.findViewById(R.id.address)).setText(r.getAddress()); // fill restaurant address
            ImageView symbol = row.findViewById(R.id.symbol);
            if (r.getType().equals("Delivery"))
                symbol.setImageResource(R.drawable.ball_green);
            else if (r.getType().equals("Sit-down"))
                symbol.setImageResource(R.drawable.ball_red);
            else if (r.getType().equals("Take-out"))
                symbol.setImageResource(R.drawable.ball_yellow);
            return (row);
        }
    }
}
