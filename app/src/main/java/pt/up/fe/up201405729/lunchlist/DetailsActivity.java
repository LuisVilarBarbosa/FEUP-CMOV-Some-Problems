package pt.up.fe.up201405729.lunchlist;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private RestaurantsHelper helper;
    private String rId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setIcon(R.drawable.rest_icon);
            bar.setDisplayShowHomeEnabled(true);
        }

        helper = new RestaurantsHelper(this);
        rId = getIntent().getStringExtra(MainActivity.ID_EXTRA);
        if (rId != null)
            load();
    }

    private void load() {
        Cursor c = helper.getById(rId);
        c.moveToFirst();
        ((EditText) findViewById(R.id.nameEditText)).setText(helper.getName(c));
        ((EditText) findViewById(R.id.addressEditText)).setText(helper.getAddress(c));
        for (int typeRadioButtonId = R.id.typeRadioButton1; typeRadioButtonId <= R.id.typeRadioButton3; typeRadioButtonId++) {
            RadioButton typeRadioButton = findViewById(typeRadioButtonId);
            if (typeRadioButton.getText().equals(helper.getType(c)))
                typeRadioButton.toggle();
        }
        ((EditText) findViewById(R.id.notesEditText)).setText(helper.getNotes(c));
        c.close();
    }

    @Override
    public void onClick(View view) {
        EditText name = findViewById(R.id.nameEditText);
        EditText address = findViewById(R.id.addressEditText);
        RadioGroup typeRadioGroup = findViewById(R.id.typeRadioGroup);
        String type = ((RadioButton) findViewById(typeRadioGroup.getCheckedRadioButtonId())).getText().toString();
        EditText notes = findViewById(R.id.notesEditText);

        if (rId == null) {
            MainActivity.currentId = helper.insert(name.getText().toString(), address.getText().toString(),
                    type, notes.getText().toString());
        } else {
            helper.update(rId, name.getText().toString(), address.getText().toString(), type,
                    notes.getText().toString());
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }
}
