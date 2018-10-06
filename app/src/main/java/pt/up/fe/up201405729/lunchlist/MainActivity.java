package pt.up.fe.up201405729.lunchlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Restaurant> rests = new ArrayList<>();
    private RestaurantAdapter adapter;

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
            }
        });
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
        adapter.add(restaurant);
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
                row = inflater.inflate(R.layout.row, null); // get our custom layout
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
