package fr.gendarmerienationale.reseauprevention31.adapter;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.getStringDate;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.activity.messages.ViewPagerFragment;
import fr.gendarmerienationale.reseauprevention31.struct.Annonce;
import fr.gendarmerienationale.reseauprevention31.util.DialogsHelper;
import java.util.List;


public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceAdapter.ViewHolder> {

    private final List<Annonce>     mAnnonces;
    private final ViewPagerFragment mViewPagerFragment;

    public AnnonceAdapter(List<Annonce> _annonces, ViewPagerFragment _viewPagerFragment) {
        this.mViewPagerFragment = _viewPagerFragment;
        this.mAnnonces = _annonces;
    }

    @Override
    public int getItemCount() {
        return mAnnonces.size();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_annonce, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        final Annonce annonce = mAnnonces.get(pos);
        holder.display(annonce);

        // Event de click long sur un message
        holder.itemView.setOnLongClickListener(view -> {
            boolean done = false;
            if (annonce.isVue()) {
                new AlertDialog.Builder(holder.getContext())
                        .setIcon(R.drawable.delete)
                        .setTitle(R.string.delete_announcement)
                        .setNegativeButton(R.string.non, (dialog, which) -> dialog.cancel())
                        .setPositiveButton(R.string.oui, (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            } else {
                done = MainActivity.sDatabaseHelper.markAnnouncementAsSeen(annonce);
                if (done) {
                    Log.d(LOG, "annonce vue ? " + annonce.isVue());
                    DialogsHelper.displayToast(holder.getContext(), R.string.marked_as_seen, Toast.LENGTH_LONG);
                    mViewPagerFragment.refreshRecycler();
                }
            }
            return done;
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTitreField;
        private final TextView mDateField;
        private final TextView mContenuField;

        private final Context mContext;

        ViewHolder(final View v) {
            super(v);

            mContext = v.getContext();
            mTitreField = v.findViewById(R.id.titreAnnonce);
            mDateField = v.findViewById(R.id.dateAnnonce);
            mContenuField = v.findViewById(R.id.contenuAnnonce);
        }

        void display(Annonce _annonce) {
            // Affiche les valeurs
            mDateField.setText(getStringDate(_annonce.getDate()));
            String texte = _annonce.getTexte();
            mTitreField.setText(texte.substring(0, texte.length() < 50 ? texte.length() : 50));
            mContenuField.setText(texte);

            Log.d(LOG, "Displaying : " + _annonce.toString());
        }

        public Context getContext() {
            return this.mContext;
        }
    }
}
