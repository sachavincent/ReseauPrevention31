package fr.gendarmerienationale.reseauprevention31.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import fr.gendarmerienationale.reseauprevention31.R;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button buttonEnvoyerMessage = findViewById(R.id.ButtonEnvoyerMessage);
        Button buttonConsulterInfos = findViewById(R.id.ButtonConsulterInfos);
        Button buttonLocaliserBrigade;
        Button buttonConseilsProtection;

        if(MainActivity.sDatabaseHelper.isUserConnected()) {
            setContentView(R.layout.activity_accueil_connecte);

            buttonLocaliserBrigade = findViewById(R.id.buttonLocaliserBrigade);
            buttonConseilsProtection = findViewById(R.id.ButtonConseilsProtection);

            buttonEnvoyerMessage.setOnClickListener(v -> startActivity(new Intent(AccueilActivity.this, EnvoyerMessageActivity.class)));
            buttonConsulterInfos.setOnClickListener(v -> startActivity(new Intent(AccueilActivity.this, ConsulterMessagesActivity.class)));
            buttonConseilsProtection.setOnClickListener(v -> startActivity(new Intent(AccueilActivity.this, ConseilsProctectionActivity.class)));
            buttonLocaliserBrigade.setOnClickListener(v -> startActivity(new Intent(AccueilActivity.this, LocaliserBrigadeActivity.class)));

        }else{
            setContentView(R.layout.activity_accueil_non_connecte);

            buttonLocaliserBrigade = findViewById(R.id.buttonLocaliserBrigade);
            buttonConseilsProtection = findViewById(R.id.ButtonConseilsProtection);

            buttonConseilsProtection.setOnClickListener(v -> startActivity(new Intent(AccueilActivity.this, ConseilsProctectionActivity.class)));
            buttonLocaliserBrigade.setOnClickListener(v -> startActivity(new Intent(AccueilActivity.this, LocaliserBrigadeActivity.class)));

        }

        ImageView imageView = findViewById(R.id.imageChambre);

        imageView.setImageResource(R.mipmap.cci);
    }

}
