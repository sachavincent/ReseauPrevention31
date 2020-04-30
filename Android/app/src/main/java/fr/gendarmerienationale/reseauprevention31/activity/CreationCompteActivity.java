package fr.gendarmerienationale.reseauprevention31.activity;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import fr.gendarmerienationale.reseauprevention31.R;

public class CreationCompteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accueil);

        ImageView imageView = findViewById(R.id.imageChambre);

        imageView.setImageResource(R.mipmap.cci);
    }

}
