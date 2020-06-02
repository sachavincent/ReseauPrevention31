package fr.gendarmerienationale.reseauprevention31.activity.messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.adapter.FilDeDiscussionAdapter;
import fr.gendarmerienationale.reseauprevention31.struct.FilDeDiscussion;
import java.util.ArrayList;
import java.util.List;

public class AllMessagesFragment extends ViewPagerFragment {

    private List<FilDeDiscussion>  mFilsDeDiscussion;
    private FilDeDiscussionAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_fils, container, false);

        super.onCreateView(inflater, container, savedInstanceState);

        return mView;
    }

    /**
     * Initialise le recycler
     */
    void initRecycler() {
        // Liste des items
        mFilsDeDiscussion = new ArrayList<>();

        // Initialise le recycler
        RecyclerView recycler = mView.findViewById(R.id.recyclerAllFils);
        recycler.setHasFixedSize(true);
        DividerItemDecoration decor = new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        recycler.addItemDecoration(decor);
        recycler.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));

        // Initialise l'adapter du recycler
        mAdapter = new FilDeDiscussionAdapter(mFilsDeDiscussion, getActivity());
        recycler.setAdapter(mAdapter);

        refreshRecycler();
    }

    /**
     * Permet de mettre à jour le Recycler
     */
    public void refreshRecycler() {
        mFilsDeDiscussion.clear();

        mFilsDeDiscussion.addAll(MainActivity.sDatabaseHelper.getAllFilDeDiscussions());

        mAdapter.notifyDataSetChanged();
    }
}