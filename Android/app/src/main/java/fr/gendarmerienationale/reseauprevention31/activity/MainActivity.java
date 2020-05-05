package fr.gendarmerienationale.reseauprevention31.activity;

import android.Manifest;
import android.Manifest.permission;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.database.DatabaseHelper;
import fr.gendarmerienationale.reseauprevention31.dialog.ConnectionDialog;
import fr.gendarmerienationale.reseauprevention31.util.Tools;
import java.util.List;
import pub.devrel.easypermissions.BuildConfig;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private final String[] appPerms       = {Manifest.permission.ACCESS_NETWORK_STATE,
            permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET};
    private final int      PERMS_CALLBACK = 5555;

    public static DatabaseHelper sDatabaseHelper;

    private MenuItem mLogoutItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setSubtitle(R.string.accueil);

        // Vérifie les permissions
        if (EasyPermissions.hasPermissions(this, appPerms))
            initApp();
        else
            EasyPermissions
                    .requestPermissions(this, getString(R.string.permissions_accepter), PERMS_CALLBACK, appPerms);
    }

    /**
     * Initialise l'appli
     */
    private void initApp() {
        if (MainActivity.sDatabaseHelper != null && !EasyPermissions.hasPermissions(this, appPerms))
            return;

        Tools.setWidth(Tools.getScreenWidth(this));

        // Initialise et ouvre la base de données
        if (MainActivity.sDatabaseHelper == null)
            MainActivity.sDatabaseHelper = new DatabaseHelper(this);

        MainActivity.sDatabaseHelper.open();


        Button buttonConnexion = findViewById(R.id.buttonConnexion);
        Button buttonCreationCompte = findViewById(R.id.buttonCreationCompte);
        Button buttonConnexionAnonyme = findViewById(R.id.buttonConnexionAnonyme);

        buttonCreationCompte.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreationCompteActivity.class);

            startActivity(intent);
        });

        buttonConnexion.setOnClickListener(v -> {
            new ConnectionDialog(MainActivity.this, this).show();
        });

        buttonConnexionAnonyme
                .setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AccueilActivity.class)));

        if (sDatabaseHelper.isUserConnected())
            startActivity(new Intent(MainActivity.this, AccueilActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        this.mLogoutItem = menu.findItem(R.id.logout);

        if (MainActivity.sDatabaseHelper.isUserConnected()) {
            this.mLogoutItem.setVisible(true);
            Button buttonConnexion = findViewById(R.id.buttonConnexion);
            buttonConnexion.setEnabled(false);
        }

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
        if (sDatabaseHelper.deleteUser()) {
            mLogoutItem.setVisible(false);
            Button buttonConnexion = findViewById(R.id.buttonConnexion);
            buttonConnexion.setEnabled(true);

            Toast.makeText(this, R.string.deconnexion_reussie, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!EasyPermissions.hasPermissions(this, appPerms))
            return;

        if (MainActivity.sDatabaseHelper == null)
            initApp();

        if (this.mLogoutItem != null) {
            Button buttonConnexion = findViewById(R.id.buttonConnexion);
            if (sDatabaseHelper.isUserConnected()) {
                mLogoutItem.setVisible(true);
                buttonConnexion.setEnabled(false);
            } else {
                mLogoutItem.setVisible(false);
                buttonConnexion.setEnabled(true);
            }
        }
    }

    @Override
    protected void onDestroy() {
        // Ferme la base de données
        if (MainActivity.sDatabaseHelper != null)
            MainActivity.sDatabaseHelper.close();

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();

        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PERMS_CALLBACK) {
            initApp();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode == PERMS_CALLBACK) {
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage(R.string.permissions_accepter)
                        .setPositiveButton(R.string.ok, (dialog, which) -> {
                            startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                            finish();
                        })
                        .create();
            } else
                finish();
        }
    }

    public MenuItem getLogoutItem() {
        return this.mLogoutItem;
    }
}
