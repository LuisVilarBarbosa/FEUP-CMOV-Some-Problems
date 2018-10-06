package pt.up.fe.up201405729.lunchlist;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setIcon(R.drawable.rest_icon);
            bar.setDisplayShowHomeEnabled(true);
        }
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
        // 'restaurant' is lost.
    }
}
