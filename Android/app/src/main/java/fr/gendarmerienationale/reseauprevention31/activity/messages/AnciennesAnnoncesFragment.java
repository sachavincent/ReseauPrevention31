package fr.gendarmerienationale.reseauprevention31.activity.messages;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.adapter.MessageAdapter;
import fr.gendarmerienationale.reseauprevention31.struct.Annonce;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnciensMessagesFragment extends Fragment {

    private static List<Annonce>  sAnnonces;
    private static MessageAdapter sAdapter;

    private Context          mContext;
    private MessagesActivity mActivity;

    private MenuItem mLogoutItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_old_messages, container, false);

        // Sous-Titre de l'action bar
        if (getActivity() instanceof MessagesActivity) {
            mActivity = (MessagesActivity) getActivity();

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        initRecycler(view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu _menu, @NonNull MenuInflater _inflater) {
        _inflater.inflate(R.menu.menu, _menu);

        this.mLogoutItem = _menu.findItem(R.id.logout);
        // La déconnexion n'est visible que si l'utilisateur est connecté
        this.mLogoutItem.setVisible(true);

        super.onCreateOptionsMenu(_menu, _inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem _item) {
        switch (_item.getItemId()) {
            case android.R.id.home:
                mActivity.onBackPressed();
                break;
            case R.id.logout:
                new AlertDialog.Builder(mContext)
                        .setTitle("Voulez-vous vous déconnecter ?")
                        .setIcon(R.drawable.logout_black)
                        .setNegativeButton(R.string.non, (dialog, which) -> dialog.dismiss())
                        .setPositiveButton(R.string.oui, (dialog, which) -> mActivity.disconnectUser())
                        .show();
                break;
        }

        return super.onOptionsItemSelected(_item);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mContext = context;
    }

    /**
     * Initialise le recycler
     */
    private void initRecycler(View view) {
        // Liste des items
        sAnnonces = new ArrayList<>();

        // Initialise le recycler
        RecyclerView recycler = view.findViewById(R.id.recyclerOldMessages);
        recycler.setHasFixedSize(true);
        DividerItemDecoration decor = new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        decor.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mContext, R.drawable.test_shape)));
        recycler.addItemDecoration(decor);
        recycler.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));

        // Initialise l'adapter du recycler
        sAdapter = new MessageAdapter(sAnnonces);
        recycler.setAdapter(sAdapter);

        refreshRecycler();
    }

    /**
     * Permet de mettre à jour le Recycler
     */
    private static void refreshRecycler() {
        sAnnonces.clear();

        sAnnonces.addAll(MainActivity.sDatabaseHelper.getOldMessages());

        sAdapter.notifyDataSetChanged();
    }

}
