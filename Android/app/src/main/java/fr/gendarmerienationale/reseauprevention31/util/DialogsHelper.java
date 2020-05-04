package fr.gendarmerienationale.reseauprevention31.util;

import android.content.Context;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import fr.gendarmerienationale.reseauprevention31.R;

public class DialogsHelper {

    /**
     * Génère un message d'erreur avec le message donné
     */
    public static void displayError(Context _context, String _message) {
        new AlertDialog.Builder(_context)
                .setTitle(R.string.erreur)
                .setMessage(_message)
                .setPositiveButton(R.string.ok, (dialog, id) -> dialog.dismiss())
                .setIcon(android.R.drawable.stat_notify_error)
                .show();
    }

    /**
     * Génère un message Toast avec le message donné et la longueur donnée
     */
    public static void displayToast(Context _context, String _message, int _length) {
        Toast.makeText(_context, _message, _length).show();
    }

    /**
     * Génère un message Toast avec le message donné et la longueur donnée
     */
    public static void displayToast(Context _context, int _idMessage, int _length) {
        Toast.makeText(_context, _idMessage, _length).show();
    }
}
