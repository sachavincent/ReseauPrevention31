package fr.gendarmerienationale.reseauprevention31.activity.messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.adapter.AnnonceAdapter;
import fr.gendarmerienationale.reseauprevention31.struct.Annonce;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NouvellesAnnoncesFragment extends ViewPagerFragment {

    private List<Annonce>  mAnnonces;
    private AnnonceAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_new_announcements, container, false);

        super.onCreateView(inflater, container, savedInstanceState);

        return mView;
    }

    /**
     * Initialise le recycler
     */
    void initRecycler() {
        // Liste des items
        mAnnonces = new ArrayList<>();

        // Initialise le recycler
        RecyclerView recycler = mView.findViewById(R.id.recyclerNewAnnouncements);
        recycler.setHasFixedSize(true);
        DividerItemDecoration decor = new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        decor.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mContext, R.drawable.test_shape)));
        recycler.addItemDecoration(decor);
        recycler.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));

        // Initialise l'adapter du recycler
        mAdapter = new AnnonceAdapter(mAnnonces, this);
        recycler.setAdapter(mAdapter);

        refreshRecycler();
    }

    /**
     * Permet de mettre à jour le Recycler
     */
    public void refreshRecycler() {
        mAnnonces.clear();

        mAnnonces.addAll(MainActivity.sDatabaseHelper.getNewAnnouncements());

        mAdapter.notifyDataSetChanged();
    }

}
