package fr.gendarmerienationale.reseauprevention31.adapter;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.getStringDate;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.struct.FilDeDiscussion;
import java.util.List;


public class FilDeDiscussionAdapter extends RecyclerView.Adapter<FilDeDiscussionAdapter.ViewHolder> {

    private final List<FilDeDiscussion> mFils;

    public FilDeDiscussionAdapter(List<FilDeDiscussion> mFils) {
        this.mFils = mFils;
    }

    @Override
    public int getItemCount() {
        return mFils.size();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_fil, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        final FilDeDiscussion fil = mFils.get(pos);
        holder.display(fil);

        // Event de click sur un fil pour le sélectionner
        holder.itemView.setOnClickListener(view -> {
            Log.d(LOG, "Opening fil");
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titre;
        private final TextView date;
        private final TextView contenu;

        ViewHolder(final View v) {
            super(v);

            titre = v.findViewById(R.id.titreMessage);
            date = v.findViewById(R.id.dateMessage);
            contenu = v.findViewById(R.id.contenuMessage);
        }

        void display(FilDeDiscussion _fil) {
            // Affiche les valeurs
            date.setText(getStringDate(_fil.get));
            String texte = _fil.getTexte();
            titre.setText(texte.substring(0, texte.length() < 50 ? texte.length() : 50));
            contenu.setText(texte);

            Log.d(LOG, "Displaying : " + _fil.toString());
        }
    }
}
