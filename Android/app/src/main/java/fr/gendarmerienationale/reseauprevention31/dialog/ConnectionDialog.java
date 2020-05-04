package fr.gendarmerienationale.reseauprevention31.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.asynctask.DatabaseDateAPICaller;
import fr.gendarmerienationale.reseauprevention31.asynctask.ConnectionAPICaller;
import fr.gendarmerienationale.reseauprevention31.util.Tools;
import java.util.Date;

public class ConnectionDialog extends Dialog {

    private MainActivity mActivity;
    private Context      mContext;

    private Button mBtnValider;

    public ConnectionDialog(@NonNull Context _context, @NonNull MainActivity _activity) {
        super(_context);

        this.mContext = _context;
        this.mActivity = _activity;
    }

    @Override
    protected void onStart() {
        super.onStart();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getWindow().setLayout((int) (Tools.getWidth() * .9), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View dialogView = inflater.inflate(R.layout.dialog_request_connection, null);
        setContentView(dialogView);

        // Event quand on appuie sur "Valider"
        mBtnValider = findViewById(R.id.btnValiderConnection);
        mBtnValider.setOnClickListener(dialog -> valider());
    }

    /**
     * Permet de valider la réponse de l'utilisateur
     */
    private void valider() {
        TextInputEditText keyField = findViewById(R.id.keyFieldConnection);
        if (keyField.getText() == null || keyField.getText().toString().isEmpty())
            return;

        switchValiderState();

        new ConnectionAPICaller(keyField.getText().toString(), mContext, mActivity.getLogoutItem(), this).execute();
    }

    public void switchValiderState() {
        if (mBtnValider != null) {
            mBtnValider.setText(R.string.valider);
            mBtnValider.setEnabled(!mBtnValider.isEnabled());

            mBtnValider.setText(mBtnValider.isEnabled() ? R.string.valider : R.string.connexion_en_cours);
        }
    }

    /**
     * Méthode appelée quand la connexion a réussie
     */
    @Override
    public void dismiss() {
        super.dismiss();

        Date lastConnectionDate = MainActivity.sDatabaseHelper.getLastDatabaseUpdateDate();
        String cleIdentification = MainActivity.sDatabaseHelper.getUserID();
        new DatabaseDateAPICaller(mContext, cleIdentification, lastConnectionDate).execute();
    }
}
