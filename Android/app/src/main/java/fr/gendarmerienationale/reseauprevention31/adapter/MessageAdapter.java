package fr.gendarmerienationale.reseauprevention31.adapter;

import static fr.gendarmerienationale.reseauprevention31.util.Tools.LOG;
import static fr.gendarmerienationale.reseauprevention31.util.Tools.getDateForInsert;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.struct.Message;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private final List<Message> mMessages;

    public MessageAdapter(List<Message> mMessages) {
        this.mMessages = mMessages;
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup _parent, int _viewType) {
        LayoutInflater inflater = LayoutInflater.from(_parent.getContext());
        View v = inflater.inflate(R.layout.item_message, _parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        final Message message = mMessages.get(pos);
        holder.display(message);
//
//        // Event de click sur un message pour le sélectionner
//        holder.itemView.setOnLongClickListener(view -> {
//            Log.d(LOG, "Long click on msg");
//            return MainActivity.sDatabaseHelper.markMessageAsSeen(message);
//        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView auteur;
        private final TextView date;
        private final TextView contenu;

        ViewHolder(final View v) {
            super(v);

            auteur = v.findViewById(R.id.auteurMessage);
            date = v.findViewById(R.id.dateMessage);
            contenu = v.findViewById(R.id.contenuMessage);
        }

        void display(Message _message) {
            // Affiche les valeurs
            date.setText(getDateForInsert(_message.getDate()));
            contenu.setText(_message.getTexte());
            auteur.setText(_message.getEmetteur().toString());

            Log.d(LOG, "Displaying : " + _message.toString());
        }
    }
}
