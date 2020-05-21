package fr.gendarmerienationale.reseauprevention31.activity;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.messages.MessagesActivity;

public class AccueilActivity extends AppCompatActivity {

    private MenuItem mLogoutItem;

    private boolean mConnected;

    private ActionBar mActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mConnected = MainActivity.sDatabaseHelper.isUserConnected();
        Button buttonEnvoyerMessage;
        Button buttonConsulterMessage;
        Button buttonLocaliserBrigade;
        Button buttonConseilsProtection;

        if (this.mConnected) {
            Log.d(LOG, "connected");
            setContentView(R.layout.activity_accueil_connecte);

//            buttonEnvoyerMessage = findViewById(R.id.buttonEnvoyerMessage);
            buttonConsulterMessage = findViewById(R.id.buttonConsulterMessageConnecte);
            buttonLocaliserBrigade = findViewById(R.id.buttonLocaliserBrigadeConnecte);
            buttonConseilsProtection = findViewById(R.id.buttonConseilsProtection);

            buttonConsulterMessage.setOnClickListener(
                    v -> startActivity(new Intent(AccueilActivity.this, MessagesActivity.class)));
            buttonConseilsProtection.setOnClickListener(
                    v -> startActivity(new Intent(AccueilActivity.this, ConseilsActivity.class)));
//            buttonLocaliserBrigade.setOnClickListener(
//                    v -> startActivity(new Intent(AccueilActivity.this, LocaliserBrigadeActivity.class)));
            buttonLocaliserBrigade.setOnClickListener(
                    v -> MainActivity.sDatabaseHelper.insertRandomMessage());

        } else {
            Log.d(LOG, "not connected");
            setContentView(R.layout.activity_accueil_non_connecte);
//
            buttonEnvoyerMessage = findViewById(R.id.buttonEnvoyerMessage);
            buttonConsulterMessage = findViewById(R.id.buttonConsulterMessage);
//            buttonLocaliserBrigade = findViewById(R.id.buttonLocaliserBrigade);
//            buttonConseilsProtection = findViewById(R.id.ButtonConseilsProtection);
//
//            buttonConseilsProtection.setOnClickListener(
//                    v -> startActivity(new Intent(AccueilActivity.this, ConseilsProctectionActivity.class)));
//            buttonLocaliserBrigade.setOnClickListener(
//                    v -> startActivity(new Intent(AccueilActivity.this, LocaliserBrigadeActivity.class)));

            buttonEnvoyerMessage
                    .setOnClickListener(v -> startActivity(new Intent(AccueilActivity.this, MainActivity.class)));
            buttonConsulterMessage
                    .setOnClickListener(v -> startActivity(new Intent(AccueilActivity.this, MainActivity.class)));

        }
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(!mConnected);
            mActionBar.setSubtitle(mConnected ? R.string.accueil : R.string.accueil_anonyme);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        AccueilActivity.super.onBackPressed();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        this.mLogoutItem = menu.findItem(R.id.logout);
        // La déconnexion n'est visible que si l'utilisateur est connecté
        this.mLogoutItem.setVisible(mConnected);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.mLogoutItem != null) {
            this.mConnected = MainActivity.sDatabaseHelper.isUserConnected();
            this.mLogoutItem.setVisible(this.mConnected);
        }

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(!mConnected);
            mActionBar.setSubtitle(mConnected ? R.string.accueil : R.string.accueil_anonyme);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Voulez-vous vous déconnecter ?")
                    .setIcon(R.drawable.logout_black)
                    .setNegativeButton(R.string.non, (dialog, which) -> dialog.dismiss())
                    .setPositiveButton(R.string.oui, (dialog, which) -> disconnectUser())
                    .show();
        }


        return super.onOptionsItemSelected(item);
    }

    private void disconnectUser() {
        if (MainActivity.sDatabaseHelper.deleteUser()) {
            Toast.makeText(this, R.string.deconnexion_reussie, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AccueilActivity.this, MainActivity.class);

            finish();
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {
        // Si l'utilisateur n'est pas connecté, lui permettre de revenir à l'activité de connexion (MainActivity)
        if (!mConnected)
            super.onBackPressed();
    }
}
