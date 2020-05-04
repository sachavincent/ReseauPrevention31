package fr.gendarmerienationale.reseauprevention31.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.android.material.textfield.TextInputEditText;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.asynctask.ConnectionAPICaller;
import fr.gendarmerienationale.reseauprevention31.asynctask.DatabaseDateAPICaller;
import fr.gendarmerienationale.reseauprevention31.asynctask.FTPDownloader;
import fr.gendarmerienationale.reseauprevention31.util.DialogsHelper;
import fr.gendarmerienationale.reseauprevention31.util.Tools;
import java.io.File;
import java.util.Collections;
import java.util.Date;

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

        setOnShowListener(
                dialog -> new FTPDownloader(mContext, this, Collections.singletonList(new File("DATABASE.CSV")))
                        .execute());
    }

    /**
     * Changement du texte à l'initialisation
     */
    public void onInit() {
        mTextView.setText(R.string.initialisation);
    }

    public void onError(String error) {
        dismiss();

        DialogsHelper.displayToast(mContext, error, Toast.LENGTH_LONG);
    }

    public void onSuccess() {
        dismiss();

        DialogsHelper.displayToast(mContext,  R.string.mise_a_jour_reussie, Toast.LENGTH_LONG);
    }

    public String onDownloadStart() {
        return mContext.getString(R.string.mise_a_jour_commencee);
    }

}
