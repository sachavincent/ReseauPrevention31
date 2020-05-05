package fr.gendarmerienationale.reseauprevention31.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import fr.gendarmerienationale.reseauprevention31.R;

public class AccueilActivity extends AppCompatActivity {

    private MenuItem mLogoutItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accueil);

        ImageView imageView = findViewById(R.id.imageChambre);

        imageView.setImageResource(R.mipmap.cci);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        this.mLogoutItem = menu.findItem(R.id.logout);
        this.mLogoutItem.setVisible(true);

        return true;
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

    }
}
