package th.o.earthquakereport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class setting extends AppCompatActivity {

    double minMagnitude;
    double maxMagnitude;
    String order, alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        RadioGroup radioGroupMagnitude = findViewById(R.id.RadioGroupMagnitude);
        radioGroupMagnitude.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.RadioGroup2:
                        minMagnitude = 2.0;
                        maxMagnitude = 3.0;
                        break;
                    case R.id.RadioGroup3:
                        minMagnitude = 3.0;
                        maxMagnitude = 4.0;
                        break;
                    case R.id.RadioGroup4:
                        minMagnitude = 4.0;
                        maxMagnitude = 5.0;
                        break;
                    case R.id.RadioGroup5:
                        minMagnitude = 5.0;
                        maxMagnitude = 6.0;
                        break;
                    case R.id.RadioGroup6:
                        minMagnitude = 6.0;
                        maxMagnitude = 7.0;
                        break;
                    case R.id.RadioGroup7:
                        minMagnitude = 7.0;
                        maxMagnitude = 8.0;
                        break;
                    case R.id.RadioGroup8:
                        minMagnitude = 8.0;
                        maxMagnitude = 10.0;
                        break;
                }
            }
        });

        RadioGroup orderGroupMagnitude = findViewById(R.id.RadioGroupOrder);
        orderGroupMagnitude.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.orderByAscendingMagnitude:
                        order = "magnitude-asc";
                        break;
                    case R.id.orderByDescendingMagnitude:
                        order = "magnitude";
                        break;
                    case R.id.orderByAscendingTime:
                        order = "time-asc";
                        break;
                    case R.id.orderByDescendingTime:
                        order = "time";
                        break;

                }
            }
        });

        RadioGroup radioGroupAlert = findViewById(R.id.RadioGroupAlert);
        radioGroupAlert.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.greenAlert:
                        alert = "green";
                        break;
                    case R.id.yellowAlert:
                        alert = "yellow";
                        break;
                    case R.id.orangeAlert:
                        alert = "orange";
                        break;
                    case R.id.redAlert:
                        alert = "red";
                        break;
                }
            }
        });

        Button applyButton = findViewById(R.id.applyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, MainActivity.class);

                intent.putExtra("minMagnitudeCode", minMagnitude);
                intent.putExtra("maxMagnitudeCode", maxMagnitude);
                intent.putExtra("orderCode", order);
                intent.putExtra("alertCode", alert);

                startActivity(intent);
            }
        });

        Button defaultButton = findViewById(R.id.defaultButton);
        defaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}