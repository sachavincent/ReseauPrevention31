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
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import fr.gendarmerienationale.reseauprevention31.R;

public abstract class ViewPagerFragment extends Fragment {


    Context          mContext;
    MessagesActivity mActivity;

    MenuItem mLogoutItem;

    View mView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        // Sous-Titre de l'action bar
        if (getActivity() instanceof MessagesActivity) {
            mActivity = (MessagesActivity) getActivity();

            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setSubtitle(R.string.nouvelles_annonces);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        initRecycler();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu _menu, @NonNull MenuInflater _inflater) {
        _inflater.inflate(R.menu.menu, _menu);

        this.mLogoutItem = _menu.findItem(R.id.logout);
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

    public abstract void refreshRecycler();

    abstract void initRecycler();
}
