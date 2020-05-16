package fr.gendarmerienationale.reseauprevention31.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.database.DatabaseHelper;

public class ConseilsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conseils);

        TextView textview = findViewById(R.id.textview_conseils);
        textview.setText("conseil");
//DatabaseHelper.getConseils()
    }
}
