package fr.gendarmerienationale.reseauprevention31.activity.messages;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.adapter.MessageAdapter;
import fr.gendarmerienationale.reseauprevention31.struct.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessagePriveActivity extends AppCompatActivity {

    private int mIdFil;

    private MenuItem mLogoutItem;

    private List<Message>  mMessages;
    private MessageAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        setContentView(R.layout.activity_messages_prives);

        String objet = getIntent().getExtras() == null ? "" : getIntent().getExtras().getString("objet_msg");
        mIdFil = getIntent().getExtras() == null ? 0 : getIntent().getExtras().getInt("id_fil");
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null && objet != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setSubtitle(objet.substring(0, objet.length() < 10 ? objet.length() : 10)
                    .concat(objet.length() > 10 ? "…" : ""));
        }

        initRecycler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        this.mLogoutItem = menu.findItem(R.id.logout);
        this.mLogoutItem.setVisible(true);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        MessagePriveActivity.super.onBackPressed();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        switch (_item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.logout:
                new AlertDialog.Builder(this)
                        .setTitle("Voulez-vous vous déconnecter ?")
                        .setIcon(R.drawable.logout_black)
                        .setNegativeButton(R.string.non, (dialog, which) -> dialog.dismiss())
                        .setPositiveButton(R.string.oui, (dialog, which) -> disconnectUser())
                        .show();
        }

        return super.onOptionsItemSelected(_item);
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }

    private void disconnectUser() {
        if (MainActivity.sDatabaseHelper.deleteUser()) {
            Toast.makeText(this, R.string.deconnexion_reussie, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MessagePriveActivity.this, MainActivity.class);

            finish();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    /**
     * Initialise le recycler
     */
    void initRecycler() {
        // Liste des items
        mMessages = new ArrayList<>();

        // Initialise le recycler
        RecyclerView recycler = findViewById(R.id.recyclerMessages);
        recycler.setHasFixedSize(true);
        DividerItemDecoration decor = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        decor.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.test_shape)));
        recycler.addItemDecoration(decor);
        recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        // Initialise l'adapter du recycler
        mAdapter = new MessageAdapter(mMessages);
        recycler.setAdapter(mAdapter);

        refreshRecycler();
    }

    /**
     * Permet de mettre à jour le Recycler
     */
    public void refreshRecycler() {
        mMessages.clear();

        mMessages.addAll(MainActivity.sDatabaseHelper.getMessagesOfFil(mIdFil));

        mAdapter.notifyDataSetChanged();
    }

}
