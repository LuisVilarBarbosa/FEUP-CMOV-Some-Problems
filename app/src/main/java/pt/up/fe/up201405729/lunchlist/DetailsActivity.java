package pt.up.fe.up201405729.lunchlist;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private int rPos;
    private LunchApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setIcon(R.drawable.rest_icon);
            bar.setDisplayShowHomeEnabled(true);
        }

        app = (LunchApp) getApplicationContext();
        rPos = getIntent().getIntExtra(MainActivity.ID_EXTRA, -1); // if not present, get -1
        if (rPos != -1)
            load();
    }

    private void load() {
        ((EditText) findViewById(R.id.nameEditText)).setText(app.current.getName());
        ((EditText) findViewById(R.id.addressEditText)).setText(app.current.getAddress());
        for (int typeRadioButtonId = R.id.typeRadioButton1; typeRadioButtonId <= R.id.typeRadioButton3; typeRadioButtonId++) {
            RadioButton typeRadioButton = findViewById(typeRadioButtonId);
            if (typeRadioButton.getText().equals(app.current.getType()))
                typeRadioButton.toggle();
        }
        ((EditText) findViewById(R.id.notesEditText)).setText(app.current.getNotes());
    }

    @Override
    public void onClick(View view) {
        if (rPos != -1)
            app.adapter.remove(app.current);
        app.current = new Restaurant();
        app.current.setName(((EditText) findViewById(R.id.nameEditText)).getText().toString());
        app.current.setAddress(((EditText) findViewById(R.id.addressEditText)).getText().toString());
        RadioGroup typeRadioGroup = findViewById(R.id.typeRadioGroup);
        app.current.setType(((RadioButton) findViewById(typeRadioGroup.getCheckedRadioButtonId())).getText().toString());
        app.current.setNotes(((EditText) findViewById(R.id.notesEditText)).getText().toString());

        if (rPos != -1)
            app.adapter.insert(app.current, rPos);
        else
            app.adapter.add(app.current);
        finish();
    }
}
