package fr.gendarmerienationale.reseauprevention31.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.AccueilActivity;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.asynctask.FTPDownloader;
import fr.gendarmerienationale.reseauprevention31.util.DialogsHelper;
import fr.gendarmerienationale.reseauprevention31.util.Tools;

public class UpdateDatabaseDialog extends Dialog {

    private Context           mContext;
    private ProgressBar       mProgressBar;
    private AppCompatTextView mTrfTitle, mTextView;
    private AppCompatButton mCancelButton;

    public UpdateDatabaseDialog(@NonNull Context _context) {
        super(_context);

        this.mContext = _context;
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
        setCanceledOnTouchOutside(false);
        setCancelable(true);

        setContentView(R.layout.dialog_update_database);

        mTextView = findViewById(R.id.updateDatabaseState);
        mTextView.setText(R.string.initialisation);

        mProgressBar = findViewById(R.id.updateDatabaseProgressbar);
        mTrfTitle = findViewById(R.id.loadingDatabaseTitle);
        mTrfTitle.setText(R.string.update);

        // Event quand on appuie sur "Annuler"
        mCancelButton = findViewById(R.id.btnAnnulerUpdateDatabase);
        mCancelButton.setOnClickListener(dialog -> cancel());

        setOnShowListener(dialog -> new FTPDownloader(mContext, this).execute());
    }

    /**
     * Changement du texte à l'initialisation
     */
    public void onInit() {
        mTextView.setText(R.string.initialisation);
    }

    /**
     * En cas d'erreur du téléchargement
     *
     * @param error le message d'erreur à afficher
     */
    public void onError(String error) {
        dismiss();

        DialogsHelper.displayToast(mContext, error, Toast.LENGTH_LONG);

        mContext.startActivity(new Intent(mContext, AccueilActivity.class));
    }

    /**
     * En cas de succès du téléchargement
     */
    public void onSuccess() {
        // Mise à jour de la base de données
        MainActivity.sDatabaseHelper.updateDatabase();

        dismiss();

        DialogsHelper.displayToast(mContext, R.string.mise_a_jour_reussie, Toast.LENGTH_LONG);


        mContext.startActivity(new Intent(mContext, AccueilActivity.class));
    }

    public String onDownloadStart() {
        return mContext.getString(R.string.mise_a_jour_commencee);
    }

}
