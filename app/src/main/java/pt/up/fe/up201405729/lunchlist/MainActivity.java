package pt.up.fe.up201405729.lunchlist;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    public final static String ID_EXTRA = "RestaurantPosition";
    public static long currentId = -1;
    private RestaurantsHelper helper;
    private Cursor model = null;
    private RestaurantAdapter adapter = null;

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
        helper = new RestaurantsHelper(this);
        model = helper.getAll();
        startManagingCursor(model);
        adapter = new RestaurantAdapter(model);
        list.setAdapter(adapter);
        list.setEmptyView(findViewById(R.id.empty_list));
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, DetailsActivity.class);
        currentId = id;
        i.putExtra(ID_EXTRA, String.valueOf(id));
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
            if (currentId != -1) {
                Cursor c = helper.getById(String.valueOf(currentId));
                c.moveToNext();
                message = String.format("%s:\n%s", helper.getName(c), helper.getNotes(c));
                c.close();
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return (true);
        } else if (item.getItemId() == R.id.add) {
            startActivity(new Intent(this, DetailsActivity.class));
            return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    class RestaurantAdapter extends CursorAdapter {

        public RestaurantAdapter(Cursor c) {
            super(MainActivity.this, c);
        }

        public void bindView(View row, Context ctxt, Cursor c) {
        }

        public View newView(Context ctxt, Cursor c, ViewGroup parent) {
            View row = getLayoutInflater().inflate(R.layout.row, parent, false);
            ((TextView) row.findViewById(R.id.title)).setText(helper.getName(c));
            ((TextView) row.findViewById(R.id.address)).setText(helper.getAddress(c));
            ImageView symbol = row.findViewById(R.id.symbol);
            if (helper.getType(c).equals("Delivery"))
                symbol.setImageResource(R.drawable.ball_green);
            else if (helper.getType(c).equals("Sit-down"))
                symbol.setImageResource(R.drawable.ball_red);
            else if (helper.getType(c).equals("Take-out"))
                symbol.setImageResource(R.drawable.ball_yellow);
            return (row);
        }
    }
}
