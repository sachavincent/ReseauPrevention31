package fr.gendarmerienationale.reseauprevention31.activity;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.database.DatabaseHelper;
import fr.gendarmerienationale.reseauprevention31.struct.Secteur;
import java.util.List;
import pub.devrel.easypermissions.BuildConfig;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private final String[] appPerms       = {Manifest.permission_group.LOCATION, Manifest.permission_group.CAMERA};
    private final int      PERMS_CALLBACK = 5555;

    public static DatabaseHelper sDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        Button boutonex = findViewById(R.id.bouton);

        boutonex.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AccueilActivity.class);

            startActivity(intent);
        });

        Secteur zone1 = Secteur.ZONE_1;
        Secteur zone2 = Secteur.ZONE_2;

        Log.d(LOG, "zone1:" + zone1.ordinal());
        Log.d(LOG, "zone2:" + zone2.ordinal());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings)
            return true;


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!EasyPermissions.hasPermissions(this, appPerms))
            return;

//        if (MainActivity.sDatabaseHelper == null)
//            initApp();
//        else
//            setListeners();
    }

    @Override
    protected void onDestroy() {
        // Ferme la base de données
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
//            initApp();
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
}