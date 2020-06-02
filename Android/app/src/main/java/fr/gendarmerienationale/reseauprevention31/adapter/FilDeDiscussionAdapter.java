package fr.gendarmerienationale.reseauprevention31.adapter;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.getStringDate;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.messages.MessagePriveActivity;
import fr.gendarmerienationale.reseauprevention31.struct.FilDeDiscussion;
import fr.gendarmerienationale.reseauprevention31.struct.Message;
import java.util.List;


public class FilDeDiscussionAdapter extends RecyclerView.Adapter<FilDeDiscussionAdapter.ViewHolder> {

    private final Activity mActivity;

    private final List<FilDeDiscussion> mFils;

    public FilDeDiscussionAdapter(List<FilDeDiscussion> mFils, Activity _activity) {
        this.mFils = mFils;
        this.mActivity = _activity;
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
            Log.d(LOG, fil.toString());
            Intent intent = new Intent(holder.itemView.getContext(), MessagePriveActivity.class);
            intent.putExtra("objet_msg", fil.getObjet());
            intent.putExtra("id_fil", fil.getId());
            holder.itemView.getContext().startActivity(intent);

            mActivity.overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titre;
        private final TextView date;
        private final TextView contenu;

        ViewHolder(final View v) {
            super(v);

            titre = v.findViewById(R.id.objetFil);
            date = v.findViewById(R.id.dateDernierMessage);
            contenu = v.findViewById(R.id.contenuDernierMessage);
        }

        void display(FilDeDiscussion _fil) {
            // Affiche les valeurs
            Message dernierMessage = _fil.getDernierMessage();
            if (dernierMessage != null) {
                date.setText(getStringDate(dernierMessage.getDate()));
                contenu.setText(dernierMessage.getTexte());
            }

            titre.setText(_fil.getObjet());

            Log.d(LOG, "Displaying : " + _fil.toString());
        }
    }
}
