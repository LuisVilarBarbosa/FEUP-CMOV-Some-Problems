package pt.up.fe.up201405729.lunchlist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TabLayout.BaseOnTabSelectedListener {
    private List<Restaurant> rests = new ArrayList<>();
    private RestaurantAdapter adapter;
    private TabLayout.Tab listTab, detailsTab;
    private View tab1, tab2;
    private Restaurant current = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setIcon(R.drawable.rest_icon);
            bar.setDisplayShowHomeEnabled(true);
        }

        ListView listView = findViewById(R.id.listView);
        adapter = new RestaurantAdapter(this, android.R.layout.simple_list_item_1, rests);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant = adapter.getItem(position);
                EditText nameEditText = findViewById(R.id.nameEditText);
                nameEditText.setText(restaurant.getName());
                EditText addressEditText = findViewById(R.id.addressEditText);
                addressEditText.setText(restaurant.getAddress());
                for (int typeRadioButtonId = R.id.typeRadioButton1; typeRadioButtonId <= R.id.typeRadioButton3; typeRadioButtonId++) {
                    RadioButton typeRadioButton = findViewById(typeRadioButtonId);
                    if (typeRadioButton.getText().equals(restaurant.getType()))
                        typeRadioButton.toggle();
                }
                EditText notesEditText = findViewById(R.id.notesEditText);
                notesEditText.setText(restaurant.getNotes());
                current = restaurant;
                detailsTab.select();
            }
        });

        TabLayout tabs = findViewById(R.id.tabs);
        listTab = tabs.newTab().setText("List");
        listTab.setIcon(R.drawable.list);
        tabs.addTab(listTab);
        detailsTab = tabs.newTab().setText("Details");
        detailsTab.setIcon(R.drawable.restaurant);
        tabs.addTab(detailsTab);
        tabs.addOnTabSelectedListener(this);
        tab1 = listView;
        tab2 = findViewById(R.id.restParams);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        Restaurant restaurant = new Restaurant();
        EditText nameEditText = findViewById(R.id.nameEditText);
        restaurant.setName(nameEditText.getText().toString());
        EditText addressEditText = findViewById(R.id.addressEditText);
        restaurant.setAddress(addressEditText.getText().toString());
        RadioGroup typeRadioGroup = findViewById(R.id.typeRadioGroup);
        RadioButton typeRadioButton = findViewById(typeRadioGroup.getCheckedRadioButtonId());
        restaurant.setType(typeRadioButton.getText().toString());
        EditText notesEditText = findViewById(R.id.notesEditText);
        restaurant.setNotes(notesEditText.getText().toString());
        adapter.add(restaurant);
        current = restaurant;
        clearKeyboard(this);
        listTab.select();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                tab1.setVisibility(View.VISIBLE);
                break;
            case 1:
                tab2.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                tab1.setVisibility(View.INVISIBLE);
                break;
            case 1:
                tab2.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    void clearKeyboard(Activity act) {
        View view = act.findViewById(android.R.id.content);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.toast) {
            String message="No restaurant selected";
            if (current!=null)
                message=current.getNotes();
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(current.getClass().getName(), current);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        current = (Restaurant) savedInstanceState.getSerializable(current.getClass().getName());
    }

    class RestaurantAdapter extends ArrayAdapter<Restaurant> {

        public RestaurantAdapter(Context context, int resourceId, List<Restaurant> objects) {
            super(context, resourceId, objects); // the Activity, row layout, and array of values
        }

        @Override
        public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.row, parent, false); // get our custom layout
            }
            Restaurant r = rests.get(position);
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
